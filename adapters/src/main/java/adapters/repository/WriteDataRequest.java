package adapters.repository;

public record WriteDataRequest(
    String measurement,
    String field,
    Double value
) {}
