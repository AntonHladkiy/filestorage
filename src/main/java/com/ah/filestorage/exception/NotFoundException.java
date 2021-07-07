package com.ah.filestorage.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException( ) {
        super( "file not found" );
    }
}