package Asynchronisation;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class AsyncReader {
    public static void main(String[] args) throws
            IOException, InterruptedException, ExecutionException {
        String fileName = "logFile";
        completionHandler(fileName);
        future(fileName);
    }

    @SuppressWarnings("unchecked")
	private static void completionHandler(String fileName)
            throws InterruptedException, IOException, ExecutionException {
        final Thread current = Thread.currentThread();
        final StringBuilder content = new StringBuilder();
        Path path = Paths.get(fileName);
        final AsynchronousFileChannel ch = AsynchronousFileChannel.open(path);
        final ByteBuffer buf = ByteBuffer.allocate(1024);
        ch.read(buf, 0, 0,
                new CompletionHandler() {
                    public void completed(Integer result, Integer length) {
                        if (result == -1) {
                            try {
                                ch.close();
                            } catch (IOException e) {
                                //ignored
                            }
                            current.interrupt();
                            return;
                        }
                        buf.flip();
                        content.append(new String(buf.array()));
                        /*while (buf.hasRemaining()) {
                            content.append((char) buf.get());
                        }*/
                        buf.clear();
                        ch.read(buf, length, length + result, this);
                    }
                    
                    public void failed(Throwable exc, Integer length) {
                        System.out.println("Failure: " + exc.toString());
                    }

					@Override
					public void completed(Object result, Object attachment) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void failed(Throwable exc, Object attachment) {
						// TODO Auto-generated method stub
						
					}
                }
        );

        System.out.println("You can use this thread for another task");
        try {
            current.join();
        } catch (InterruptedException e) {
            //ignored
        }
        System.out.println(content);
    }

    private static void future(String fileName)
            throws InterruptedException, IOException, ExecutionException {
        StringBuilder content = new StringBuilder();

        int start = 0;
        Path path = Paths.get(fileName);
        AsynchronousFileChannel ch = AsynchronousFileChannel.open(path);
        ByteBuffer buf = ByteBuffer.allocate(1024);
        Future result = ch.read(buf, start);
        int length;
        while ((length = (int) result.get()) != -1) {
            while (!result.isDone()) {
                System.out.println("You can use this thread for another task");
                Thread.sleep(100);
            }
            buf.flip();
            content.append(new String(buf.array()));
            /*while (buf.hasRemaining()) {
                content.append((char) buf.get());
            }*/
            buf.clear();
            result = ch.read(buf, start);
            start += length;
        }
        ch.close();
        System.out.println(content);
    }
}