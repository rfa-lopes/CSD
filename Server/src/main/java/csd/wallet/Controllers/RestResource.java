package csd.wallet.Controllers;

import csd.wallet.Replication.Operations.Result;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.ResponseEntity;

public class RestResource {

	protected <T> ResponseEntity<T> getResponse(Result<T> result) {
		if (result.isOK())
			return ResponseEntity.ok(result.getResult());
		else
			return statusCode(result);
	}

	static protected ResponseEntity statusCode(Result<?> result) {
		switch (result.getError()) {
		case BAD_REQUEST:
			return ResponseEntity.badRequest().build();
		case NOT_FOUND:
			return ResponseEntity.notFound().build();
		case OK:
			return result.getResult() == null ? ResponseEntity.noContent().build() : ResponseEntity.ok().build();
		case NOT_IMPLEMENTED:
			return ResponseEntity.status(NOT_IMPLEMENTED).build();
		case TIME_OUT:
			return ResponseEntity.status(REQUEST_TIMEOUT).build();
		case UNAUTHORIZED:
			return ResponseEntity.status(UNAUTHORIZED).build();
		default:
			return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
		}
	}
}
