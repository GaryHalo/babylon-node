pub(crate) const MAX_STREAM_COUNT_PER_REQUEST: u16 = 10000;

// TODO - Change this to be slightly larger than the double the max transaction payload size.
// (We double due to the hex encoding of the payload)
pub(crate) const LARGE_REQUEST_MAX_BYTES: usize = 3 * 1024 * 1024;
