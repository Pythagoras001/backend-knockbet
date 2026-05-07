package com.knockbet.backend_knockbet.Reglas;

public class MetricasDeScore {

    // --- ACTIVIDAD ---
    // Escala temporal de inactividad (en meses).
    // Controla qué tan rápido decae el rendimiento por inactividad.
    // Es el parámetro de la función exponencial: a mayor valor → caída más lenta.
    public static final double ESCALA_INACTIVIDAD_MESES = 6.0;

    // --- TIEMPO EN PELEA ---
    // Duración de referencia (en minutos) para normalizar el tiempo promedio de pelea.
    // Define el punto a partir del cual la duración se considera "alta" y penaliza el score.
    // Ej: 15 implica que peleas de 15 min o más reciben valor cercano a 0.
    public static final double DURACION_REFERENCIA_PELEA = 15.0;
}
