package com.knockbet.backend_knockbet.Reglas;

public class MetricasDeNegocio {
    // Margen aplicado a las probabilidades para generar beneficio (overround).
    // Ej: 1.05 implica ~5% de ventaja para la casa sobre probabilidades justas.
    public static final double MARGEN_DE_GANANCIA = 1.05;

    // Factor que controla qué tan agresivamente se ajustan las probabilidades
    // (y por ende las cuotas) en función de la exposición (liability).
    // Valores altos → cambios más rápidos en cuotas ante apuestas.
    public static final double FACTOR_RIESGO = 2.0;

    // Umbral máximo de exposición (liability / bankroll) permitido por resultado.
    // Si se supera este valor, la cuota se considera "LLENA" y deja de aceptar apuestas.
    // Ej: 0.8 significa que ese resultado ya compromete el 80% del bankroll.
    public static final double LIMITE_EXP = 0.8;

    /// /// ///

    // Exponente aplicado a la exposición (liability / bankroll) para modelar
    // la sensibilidad no lineal del sistema ante el riesgo.
    // Define la forma de la curva de ajuste:
    // - 1.0  → comportamiento lineal
    // - >1.0 → reacción progresiva (más sensible a exposiciones altas)
    // - <1.0 → reacción más suave
    // Ej: 1.5 implica que el impacto del riesgo crece de forma acelerada
    // a medida que la exposición aumenta.
    public static final double EXPONENTE_RIESGO = 1.5;

    // Límite máximo del ajuste aplicado a las probabilidades en función del riesgo.
    // Evita que el impacto del mercado (apuestas) distorsione excesivamente el modelo base.
    // Se aplica después de escalar por el FACTOR_RIESGO.
    // Ej: 1.5 implica que el incremento máximo será de +150% sobre la probabilidad base
    // antes del proceso de normalización.
    // Este valor actúa como mecanismo de estabilidad del sistema.
    public static final double MAX_AJUSTE_RIESGO = 1.5;

    /// /// ///

    // Límite máximo de cuota para el resultado de empate.
    // Se utiliza para evitar valores extremadamente altos debido a la muy baja probabilidad del empate
    // en deportes de combate (evento raro).
    // NOTA: Este límite puede alejar la cuota de su valor probabilístico real (infrapago),
    // ya que el empate suele tener cuotas naturalmente altas.
    // Ajustar según el nivel de realismo deseado y control de riesgo del sistema.
    public static final double MAX_CUOTA_EMPATE = 3.0;

    // Límite máximo de cuota para la victoria del peleador A.
    // Se utiliza para evitar cuotas excesivamente altas en escenarios donde el peleador A es
    // considerado muy improbable (underdog extremo).
    // NOTA: Limitar esta cuota puede reducir la capacidad del sistema para equilibrar el mercado,
    // ya que cuotas altas incentivan apuestas en el lado menos favorecido.
    // Debe configurarse con cuidado para no distorsionar el balance natural de probabilidades.
    public static final double MAX_CUOTA_PELEADOR_A = 10.0;

    // Límite máximo de cuota para la victoria del peleador B.
    // Cumple el mismo propósito que en el caso del peleador A: evitar cuotas extremas cuando B es
    // altamente improbable de ganar.
    // NOTA: Un límite demasiado bajo puede impedir que el sistema atraiga apuestas hacia este resultado,
    // dificultando el equilibrio del riesgo.
    // Ajustar en función de la estrategia del sistema (controlado vs orientado a mercado).
    public static final double MAX_CUOTA_PELEADOR_B = 10.0;
}
