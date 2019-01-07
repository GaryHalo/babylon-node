package com.radixdlt.client.core.network.epics;

import com.radixdlt.client.core.network.RadixNetworkEpic;
import com.radixdlt.client.core.network.RadixNetworkState;
import com.radixdlt.client.core.network.RadixNodeAction;
import com.radixdlt.client.core.network.actions.JsonRpcAction;
import com.radixdlt.client.core.network.actions.JsonRpcAction.JsonRpcActionType;
import com.radixdlt.client.core.network.epics.WebSocketsEpic.WebSockets;
import io.reactivex.Observable;
import java.util.concurrent.TimeUnit;

public class RadixJsonRpcAutoCloseEpic implements RadixNetworkEpic {
	private final WebSockets webSockets;
	public RadixJsonRpcAutoCloseEpic(WebSockets webSockets) {
		this.webSockets = webSockets;
	}

	@Override
	public Observable<RadixNodeAction> epic(Observable<RadixNodeAction> actions, Observable<RadixNetworkState> networkState) {
		return
			actions
				.filter(a -> a instanceof JsonRpcAction)
				.map(JsonRpcAction.class::cast)
				.filter(a -> a.getJsonRpcActionType().equals(JsonRpcActionType.RESULT))
				.delay(5, TimeUnit.SECONDS)
				.doOnNext(a -> webSockets.get(a.getNode()).close())
				.ignoreElements()
				.toObservable();
	}
}
