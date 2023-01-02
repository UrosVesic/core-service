package rs.urosvesic.coreservice.mapper;

import rs.urosvesic.coreservice.dto.Dto;
import rs.urosvesic.coreservice.model.MyEntity;

public abstract class GenericResponseMapper<D extends Dto, E extends MyEntity> {

    protected abstract D toDto(E entity);
}
