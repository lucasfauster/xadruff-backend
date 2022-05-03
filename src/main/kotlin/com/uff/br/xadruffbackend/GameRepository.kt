package com.uff.br.xadruffbackend

import com.uff.br.xadruffbackend.model.GameEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ChessRepository: JpaRepository<GameEntity, String>