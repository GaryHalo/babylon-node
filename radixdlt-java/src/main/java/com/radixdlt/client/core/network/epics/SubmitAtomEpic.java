package com.radixdlt.client.core.network.epics;

import com.radixdlt.client.core.network.RadixNetworkEpic;
import com.radixdlt.client.core.network.RadixNetworkState;
import com.radixdlt.client.core.network.RadixNodeAction;
import com.radixdlt.client.core.network.actions.SubmitAtomAction;
import com.radixdlt.client.core.network.actions.SubmitAtomAction.SubmitAtomActionType;
import com.radixdlt.client.core.network.epics.WebSocketsEpic.WebSockets;
import com.radixdlt.client.core.network.jsonrpc.RadixJsonRpcClient;
import com.radixdlt.client.core.network.websocket.WebSocketClient;
import com.radixdlt.client.core.network.websocket.WebSocketException;
import com.radixdlt.client.core.network.websocket.WebSocketStatus;
import com.radixdlt.client.core.util.IncreasingRetryTimer;
import io.reactivex.Observable;
import java.util.concurrent.TimeUnit;

public class SubmitAtomEpic implements RadixNetworkEpic {
	private final WebSockets webSockets;

	public SubmitAtomEpic(WebSockets webSockets) {
		this.webSockets = webSockets;
	}

	@Override
	public Observable<RadixNodeAction> epic(Observable<RadixNodeAction> actions, Observable<RadixNetworkState> networkState) {
		return actions
			.filter(u -> u instanceof SubmitAtomAction)
			.map(SubmitAtomAction.class::cast)
			.filter(update -> update.getType().equals(SubmitAtomActionType.SUBMIT))
			.flatMap(u -> {
				final WebSocketClient ws = webSockets.get(u.getNode());
				return ws.getState()
					.doOnNext(s -> {
						if (s.equals(WebSocketStatus.DISCONNECTED)) {
							ws.connect();
						}
					})
					.filter(s -> s.equals(WebSocketStatus.CONNECTED))
					.firstOrError()
					.flatMapObservable(i -> {
						RadixJsonRpcClient jsonRpcClient = new RadixJsonRpcClient(ws);
						return jsonRpcClient.submitAtom(u.getAtom())
							.doOnError(Throwable::printStackTrace)
							.map(nodeUpdate -> SubmitAtomAction.update(
								u.getUuid(),
								u.getAtom(),
								nodeUpdate,
								u.getNode()
							))
							.retryWhen(new IncreasingRetryTimer(WebSocketException.class))
							// TODO: Better way of cleanup?
							.doFinally(() -> Observable.timer(5, TimeUnit.SECONDS).subscribe(t -> ws.close()));
					});
			});
	}
}
