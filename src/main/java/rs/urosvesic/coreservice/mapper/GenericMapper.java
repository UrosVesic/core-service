package rs.urosvesic.coreservice.mapper;


import rs.urosvesic.coreservice.dto.Dto;
import rs.urosvesic.coreservice.model.MyEntity;

/**
 * @author UrosVesic
 */

public interface GenericMapper<D extends Dto, E extends MyEntity> {

    E toEntity(D dto);

    D toDto(E entity);
}
