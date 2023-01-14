package com.hermes.ithermes.presentation.dto.contents;

public interface DtoInterface <E extends EntityInterface, D extends DtoInterface>{

  D convertEntity(E entity);

}
