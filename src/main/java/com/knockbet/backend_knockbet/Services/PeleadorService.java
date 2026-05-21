package com.knockbet.backend_knockbet.Services;

import com.knockbet.backend_knockbet.Models.EstrucEncuentro.EstadoPelea;
import com.knockbet.backend_knockbet.Models.Peleador.*;
import com.knockbet.backend_knockbet.Models.dto.DtoEditPeleador;
import com.knockbet.backend_knockbet.Models.dto.DtoPeleador;
import com.knockbet.backend_knockbet.Reglas.MetricasDeScore;
import com.knockbet.backend_knockbet.Repository.PeleaRepository;
import com.knockbet.backend_knockbet.Repository.PeleadorRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class PeleadorService {

    private final PeleadorRepository peleadorRepository;
    private final PeleaRepository peleaRepository;

    public void registrarPeleador(DtoPeleador dtoPeleador, MultipartFile img) throws Exception{
        try {
            if (dtoPeleador.esNuevo()) registrarPeleadorNovato(dtoPeleador, img);
            else registrarPeleadorExperiencia(dtoPeleador, img);

        }catch (Exception e){
            throw new Exception(e);
        }
    }

    private void registrarPeleadorNovato(DtoPeleador dtoPeleador, MultipartFile img) throws Exception{
        try {
            CategoriaPeso categoriaPeso = CategoriaPeso.calcularTipoPeso(dtoPeleador.peso());

            FisicoData fisicoData = FisicoData.builder()
                    .peso(dtoPeleador.peso())
                    .altura(dtoPeleador.altura())
                    .alcance(dtoPeleador.alcance())
                    .edad(dtoPeleador.edad())
                    .categoriaPeso(categoriaPeso)
                    .build();

            ActividadData actividadData = ActividadData.builder()
                    .ultimaPelea(null)
                    .duracionPromedioEnPelea((int) MetricasDeScore.DURACION_REFERENCIA_PELEA / 2)
                    .build();

            HistorialData historialData = HistorialData.builder()
                    .victorias(0)
                    .derrotas(0)
                    .empates(0)
                    .totalPeleas(0)
                    .rachaActual(0)
                    .rachaHistorica(0)
                    .build();

            Peleador peleador = Peleador.builder()
                    .imgUrl(registrarImgPeleador(dtoPeleador.nombre(), img))
                    .nombre(dtoPeleador.nombre())
                    .apodo(dtoPeleador.apodo())
                    .genero(dtoPeleador.genero())
                    .actividadData(actividadData)
                    .fisicoData(fisicoData)
                    .historialData(historialData)
                    .estadoActividad(true)
                    .build();

            peleadorRepository.save(peleador);

        }catch (Exception e){
            throw new Exception(e);
        }
    }

    private void registrarPeleadorExperiencia(DtoPeleador dtoPeleador, MultipartFile img) throws Exception{
        try {
            CategoriaPeso categoriaPeso = CategoriaPeso.calcularTipoPeso(dtoPeleador.peso());

            ActividadData actividadData = ActividadData.builder()
                    .ultimaPelea(dtoPeleador.ultimaPelea())
                    .duracionPromedioEnPelea(dtoPeleador.duracionPromedioEnPelea())
                    .build();

            FisicoData fisicoData = FisicoData.builder()
                    .peso(dtoPeleador.peso())
                    .altura(dtoPeleador.altura())
                    .alcance(dtoPeleador.alcance())
                    .edad(dtoPeleador.edad())
                    .categoriaPeso(categoriaPeso)
                    .build();

            int totalPeleas = dtoPeleador.victorias() + dtoPeleador.derrotas() + dtoPeleador.empates();

            HistorialData historialData = HistorialData.builder()
                    .victorias(dtoPeleador.victorias())
                    .derrotas(dtoPeleador.derrotas())
                    .empates(dtoPeleador.empates())
                    .totalPeleas(totalPeleas)
                    .rachaActual(dtoPeleador.rachaActual())
                    .rachaHistorica(dtoPeleador.rachaHistorica())
                    .build();

            Peleador peleador = Peleador.builder()
                    .imgUrl(registrarImgPeleador(dtoPeleador.nombre(), img))
                    .nombre(dtoPeleador.nombre())
                    .apodo(dtoPeleador.apodo())
                    .genero(dtoPeleador.genero())
                    .actividadData(actividadData)
                    .fisicoData(fisicoData)
                    .historialData(historialData)
                    .estadoActividad(true)
                    .build();

            peleadorRepository.save(peleador);
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    private String registrarImgPeleador(String nombrePeleador, MultipartFile img) throws IOException {

        String fileName = nombrePeleador + "_" + UUID.randomUUID();

        Path uploadPath = Paths.get("uploads");

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        Path filePath = uploadPath.resolve(fileName);
        Files.copy(img.getInputStream(), filePath);

        return "/uploads/" + fileName;
    }

    @Transactional
    public void cambiarEstadoDeActividad(UUID idFigther) throws Exception{
        try {
            Peleador peleador = obtenerPeladorId(idFigther);
            if (peleador.isEstadoActividad()){
                if (peleaRepository.existePeleaActiva(peleador,List.of(EstadoPelea.PROGRAMADA,EstadoPelea.EN_DUELO))){
                    throw new Exception("El peleador esta o tiene una pelea programada");
                }
            }
            peleador.setEstadoActividad(!peleador.isEstadoActividad());

        }catch (Exception e){
            throw new Exception(e);
        }
    }

    @Transactional
    public void actulizarDatosPeleador(DtoEditPeleador dtoEditPeleador) throws Exception{
        try {
            Peleador peleador = obtenerPeladorId(dtoEditPeleador.id());

            peleador.setNombre(dtoEditPeleador.nombre());
            peleador.setApodo(dtoEditPeleador.apodo());
            peleador.getFisicoData().setPeso(dtoEditPeleador.peso());
            peleador.getFisicoData().setAltura(dtoEditPeleador.altura());
            peleador.getFisicoData().setAlcance(dtoEditPeleador.alcance());
            peleador.getFisicoData().setEdad(dtoEditPeleador.edad());

        }catch (Exception e){
            throw new Exception(e);
        }
    }

    public Peleador obtenerPeladorId(UUID idFigther) throws Exception{
        try {
            return peleadorRepository.findById(idFigther)
                    .orElseThrow(() -> new Exception("Peleador no encontrado: " + idFigther));
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    public List<Peleador> obtenerPeleadores() throws Exception{
        try {
            return peleadorRepository.findAll();
        }catch (Exception e){
            throw new Exception(e);
        }
    }

}
