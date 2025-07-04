package com.example.flashcardas.utils;

import java.util.UUID;

/**
 * Utility per la generazione di ID univoci.
 *
 * - Usa UUID per creare stringhe uniche come identificatori.
 */


public class IdUtils {
    public static String generateId() {
        return UUID.randomUUID().toString();
    }
}
