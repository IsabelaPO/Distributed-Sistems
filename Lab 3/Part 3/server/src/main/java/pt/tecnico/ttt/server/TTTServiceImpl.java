package pt.tecnico.ttt.server;

import io.grpc.stub.StreamObserver;
import pt.tecnico.ttt.*;

public class TTTServiceImpl extends TTTGrpc.TTTImplBase {

	/** Game implementation. */
	private TTTGame ttt = new TTTGame();

	@Override
	public void currentBoard(CurrentBoardRequest request, StreamObserver<CurrentBoardResponse> responseObserver) {
		// StreamObserver is used to represent the gRPC stream between the server and
		// client in order to send the appropriate responses (or errors, if any occur).

		CurrentBoardResponse response = CurrentBoardResponse.newBuilder().setBoard(ttt.toString()).build();

		// Send a single response through the stream.
		responseObserver.onNext(response);
		// Notify the client that the operation has been completed.
		responseObserver.onCompleted();
	}

	public void play(playRequest request,StreamObserver<playResponce> responceObserver){
		int row = request.getRow();
		int column = request.getColumn();
		int player = request.getPlayer();
		PlayResult result = ttt.play(row,column, player);
		playResponce responce = playResponce.newBuilder().setResult(result).build();
		responceObserver.onNext(responce);
		responceObserver.onCompleted();
	}

	public void checkWinner(checkWinnerRequest request, StreamObserver<checkWinnerResponce> responceObserver){
		int player = ttt.checkWinner();
		checkWinnerResponce responce = checkWinnerResponce.newBuilder().setPlayer(player).build();
		responceObserver.onNext(responce);
		responceObserver.onCompleted();;
	}

}
