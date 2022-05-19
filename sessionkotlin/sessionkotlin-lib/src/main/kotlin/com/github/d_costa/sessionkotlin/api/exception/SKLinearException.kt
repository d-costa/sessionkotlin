package com.github.d_costa.sessionkotlin.api.exception

/**
 * Thrown when there is an attempt to use an endpoint more than once.
 */
public class SKLinearException : SessionKotlinAPIException("There was an attempt to ignore linear usage of an endpoint.")
