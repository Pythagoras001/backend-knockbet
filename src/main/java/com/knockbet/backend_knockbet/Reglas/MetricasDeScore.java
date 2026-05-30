package com.knockbet.backend_knockbet.Reglas;

public class MetricasDeScore {


    // --- HISTORIAL ---
    // Suavizado bayesiano para win rate (evita extremos con pocas peleas)
    public static final int K_SMOOTHING = 5;

    // Valor base asumido en ausencia de datos (0.5 = 50%)
    public static final double PRIORS_WINRATE = 0.5;

    // Umbrales de normalización
    public static final double MAX_PELEAS_EXPERIENCIA = 20.0;
    public static final double MAX_DERROTAS = 10.0;
    public static final double MAX_RACHA = 5.0;
    public static final double MAX_RACHA_HISTORICA = 10.0;


    // --- FÍSICO ---
    // Edad considerada como pico de rendimiento físico (prime)
    public static final double EDAD_OPTIMA = 28.0;

    // Rango de tolerancia alrededor de la edad óptima.
    // Define qué tan rápido disminuye el rendimiento al alejarse del pico.
    public static final double RANGO_EDAD = 15.0;


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


    // --- PESOS DEL MODELO DE SCORE ---
    // IMPORTANTE: La suma no necesariamente debe ser 1 debido al término negativo (derrotas).

    // Historial
    public static final double PESO_WIN_RATE = 0.25;
    public static final double PESO_EXPERIENCIA = 0.10;
    public static final double PESO_RACHA = 0.10;
    public static final double PESO_RACHA_HISTORICA = 0.05;
    public static final double PESO_DERROTAS = 0.10; // se resta

    // Físico
    public static final double PESO_EDAD = 0.10;

    // Actividad
    public static final double PESO_ACTIVIDAD = 0.20;

    // Estilo de pelea
    public static final double PESO_DURACION = 0.10;


    // --- ESCALAS DE NORMALIZACIÓN FÍSICA ---
    // Diferencia de altura (cm) considerada máxima para ventaja total
    public static final double ESCALA_ALTURA = 50.0;

    // Diferencia de alcance (cm) para ventaja total
    public static final double ESCALA_ALCANCE = 30.0;

    // Diferencia de peso (kg) para ventaja total
    public static final double ESCALA_PESO = 15.0;


    // --- PESOS DE VENTAJA FÍSICA ---
    // Impacto de la altura en el score
    public static final double PESO_ALTURA = 0.05;

    // Impacto del alcance (más importante en combate)
    public static final double PESO_ALCANCE = 0.10;

    // Impacto del peso (menor en tu modelo actual)
    public static final double PESO_PESO = 0.02;


    // FACTORES DE PROBABILIDAD DE VICTORIA

    // Factor de escala de la función logística.
    // Controla la sensibilidad de la probabilidad ante diferencias de score.
    // Valores altos → modelo más "seguro" (probabilidades extremas)
    // Valores bajos → modelo más "conservador" (más cercano a 50/50)
    public static final double ESCALA_LOGISTICA = 5.0;

    // Probabilidad base de empate cuando los peleadores son muy parejos.
    // En MMA suele ser baja; ajustar según datos reales (0.01–0.03 típico).
    public static final double PROB_BASE_EMPATE = 0.02;

    // Sensibilidad del empate frente a la diferencia de nivel (|eloA - eloB|).
    // Valores altos → el empate cae muy rápido cuando hay diferencia.
    // Valores bajos → el empate persiste incluso con diferencias.
    public static final double SENSIBILIDAD_EMPATE = 3.0;


}
