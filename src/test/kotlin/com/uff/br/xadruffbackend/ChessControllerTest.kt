package com.uff.br.xadruffbackend

import com.jayway.jsonpath.JsonPath
import com.uff.br.xadruffbackend.dto.Position
import com.uff.br.xadruffbackend.dto.enum.Color
import com.uff.br.xadruffbackend.dto.enum.EndgameMessage
import org.hamcrest.Matchers
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.util.regex.Pattern

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Suppress("LargeClass")
class ChessControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun `should return bad request in create game when start-by is an invalid text`() {
        mockMvc.post("/chess/new-game") {
            param("start-by", "BANANA")
            param("level", "BEGINNER")
            contentType = MediaType.APPLICATION_JSON
        }
            .andDo { print() }
            .andExpect { status { isBadRequest() } }
            .andExpect {
                content {
                    contentType(MediaType.APPLICATION_JSON)
                }
            }
            .andExpect {
                jsonPath("$.message", Matchers.any(String::class.java))
            }
    }

    @Test
    fun `should return bad request in create game when level is an invalid text`() {
        mockMvc.post("/chess/new-game") {
            param("start-by", "AI")
            param("level", "BANANA")
            contentType = MediaType.APPLICATION_JSON
        }
            .andDo { print() }
            .andExpect { status { isBadRequest() } }
            .andExpect {
                content {
                    contentType(MediaType.APPLICATION_JSON)
                }
            }
            .andExpect {
                jsonPath("$.message", Matchers.any(String::class.java))
            }
    }

    @Test
    fun `should return bad request in create game when start-by is null`() {
        mockMvc.post("/chess/new-game") {
            param("level", "BEGINNER")
            contentType = MediaType.APPLICATION_JSON
        }
            .andDo { print() }
            .andExpect { status { isBadRequest() } }
            .andExpect {
                content {
                    contentType(MediaType.APPLICATION_JSON)
                }
            }
            .andExpect {
                jsonPath("$.message", Matchers.any(String::class.java))
            }
    }

    @Test
    fun `should return bad request in create game when level is null`() {
        mockMvc.post("/chess/new-game") {
            param("start-by", "AI")
            contentType = MediaType.APPLICATION_JSON
        }
            .andDo { print() }
            .andExpect { status { isBadRequest() } }
            .andExpect {
                content {
                    contentType(MediaType.APPLICATION_JSON)
                }
            }
            .andExpect {
                jsonPath("$.message", Matchers.any(String::class.java))
            }
    }

    @Test
    fun `should return bad request in create game when board-request is with lesser number of rows than 8`() {
        mockMvc.post("/chess/new-game") {
            param("start-by", "AI")
            param("level", "BEGINNER")
            contentType = MediaType.APPLICATION_JSON
            content = """{
                "positions": [["K", "K", "K", "K", "K", "K", "K", "K"]],
                "turn_color": "WHITE"
                }
            """.trimMargin()
        }
            .andDo { print() }
            .andExpect { status { isBadRequest() } }
            .andExpect {
                content {
                    contentType(MediaType.APPLICATION_JSON)
                }
            }
            .andExpect {
                jsonPath("$.message", Matchers.any(String::class.java))
            }
    }

    @Test
    fun `should return bad request in create game when board-request is with greater number of rows than 8`() {
        mockMvc.post("/chess/new-game") {
            param("start-by", "AI")
            param("level", "BEGINNER")
            contentType = MediaType.APPLICATION_JSON
            content = """{
                "positions": [
                ["K", "K", "K", "K", "K", "K", "K", "K"],
                ["K", "K", "K", "K", "K", "K", "K", "K"],
                ["K", "K", "K", "K", "K", "K", "K", "K"],
                ["K", "K", "K", "K", "K", "K", "K", "K"],
                ["K", "K", "K", "K", "K", "K", "K", "K"],
                ["K", "K", "K", "K", "K", "K", "K", "K"],
                ["K", "K", "K", "K", "K", "K", "K", "K"],
                ["K", "K", "K", "K", "K", "K", "K", "K"],
                ["K", "K", "K", "K", "K", "K", "K", "K"]            
                ],
                "turn_color": "WHITE"
                }
            """.trimMargin()
        }
            .andDo { print() }
            .andExpect { status { isBadRequest() } }
            .andExpect {
                content {
                    contentType(MediaType.APPLICATION_JSON)
                }
            }
            .andExpect {
                jsonPath("$.message", Matchers.any(String::class.java))
            }
    }

    @Test
    fun `should return bad request in create game when board-request is with lesser number of columns than 8`() {
        mockMvc.post("/chess/new-game") {
            param("start-by", "AI")
            param("level", "BEGINNER")
            contentType = MediaType.APPLICATION_JSON
            content = """{
                "positions": [
                    ["K", "K", "K", "K", "K", "K", "K"],
                    ["K", "K", "K", "K", "K", "K", "K"],
                    ["K", "K", "K", "K", "K", "K", "K"],
                    ["K", "K", "K", "K", "K", "K", "K"],
                    ["K", "K", "K", "K", "K", "K", "K"],
                    ["K", "K", "K", "K", "K", "K", "K"],
                    ["K", "K", "K", "K", "K", "K", "K"],
                    ["K", "K", "K", "K", "K", "K", "K"],
                ],
                "turn_color": "WHITE"
                }
            """.trimMargin()
        }
            .andDo { print() }
            .andExpect { status { isBadRequest() } }
            .andExpect {
                content {
                    contentType(MediaType.APPLICATION_JSON)
                }
            }
            .andExpect {
                jsonPath("$.message", Matchers.any(String::class.java))
            }
    }

    @Test
    fun `should return bad request in create game when board-request is with greater number of columns than 8`() {
        mockMvc.post("/chess/new-game") {
            param("start-by", "AI")
            param("level", "BEGINNER")
            contentType = MediaType.APPLICATION_JSON
            content = """{
                "positions": [
                    ["K", "K", "K", "K", "K", "K", "K", "K", "K"],
                    ["K", "K", "K", "K", "K", "K", "K", "K", "K"],
                    ["K", "K", "K", "K", "K", "K", "K", "K", "K"],
                    ["K", "K", "K", "K", "K", "K", "K", "K", "K"],
                    ["K", "K", "K", "K", "K", "K", "K", "K", "K"],
                    ["K", "K", "K", "K", "K", "K", "K", "K", "K"],
                    ["K", "K", "K", "K", "K", "K", "K", "K", "K"],
                    ["K", "K", "K", "K", "K", "K", "K", "K", "K"],
                ],
                "turn_color": "WHITE"
                }
            """.trimMargin()
        }
            .andDo { print() }
            .andExpect { status { isBadRequest() } }
            .andExpect {
                content {
                    contentType(MediaType.APPLICATION_JSON)
                }
            }
            .andExpect {
                jsonPath("$.message", Matchers.any(String::class.java))
            }
    }

    @Test
    fun `should return bad request in create game when board-request has a piece with invalid value`() {
        mockMvc.post("/chess/new-game") {
            param("start-by", "AI")
            param("level", "BEGINNER")
            contentType = MediaType.APPLICATION_JSON
            content = """{
                "positions": [
                ["L", "K", "K", "K", "K", "K", "K", "K", "K", "K", "K", "K", "K"],
                ["K", "K", "K", "K", "K", "K", "K", "K", "K", "K", "K", "K", "K"],
                ["K", "K", "K", "K", "K", "K", "K", "K", "K", "K", "K", "K", "K"],
                ["K", "K", "K", "K", "K", "K", "K", "K", "K", "K", "K", "K", "K"],
                ["K", "K", "K", "K", "K", "K", "K", "K", "K", "K", "K", "K", "K"],
                ["K", "K", "K", "K", "K", "K", "K", "K", "K", "K", "K", "K", "K"],
                ["K", "K", "K", "K", "K", "K", "K", "K", "K", "K", "K", "K", "K"],
                ["K", "K", "K", "K", "K", "K", "K", "K", "K", "K", "K", "K", "K"],
                ["K", "K", "K", "K", "K", "K", "K", "K", "K", "K", "K", "K", "K"],
                ["K", "K", "K", "K", "K", "K", "K", "K", "K", "K", "K", "K", "K"],
                ["K", "K", "K", "K", "K", "K", "K", "K", "K", "K", "K", "K", "K"],
                ["K", "K", "K", "K", "K", "K", "K", "K", "K", "K", "K", "K", "K"],
                ["K", "K", "K", "K", "K", "K", "K", "K", "K", "K", "K", "K", "K"]                
                ],
                "turn_color": "WHITE"
                }
            """.trimMargin()
        }
            .andDo { print() }
            .andExpect { status { isBadRequest() } }
            .andExpect {
                content {
                    contentType(MediaType.APPLICATION_JSON)
                }
            }
            .andExpect {
                jsonPath("$.message", Matchers.any(String::class.java))
            }
    }

    @Test
    fun `should return bad request in create game when turn-color is an invalid text`() {
        mockMvc.post("/chess/new-game") {
            param("start-by", "AI")
            param("level", "BEGINNER")
            contentType = MediaType.APPLICATION_JSON
            content = """{
                "positions": [
                ["K", "K", "K", "K", "K", "K", "K", "K"],
                ["K", "K", "K", "K", "K", "K", "K", "K"],
                ["K", "K", "K", "K", "K", "K", "K", "K"],   
                ["K", "K", "K", "K", "K", "K", "K", "K"],   
                ["K", "K", "K", "K", "K", "K", "K", "K"],   
                ["K", "K", "K", "K", "K", "K", "K", "K"],   
                ["K", "K", "K", "K", "K", "K", "K", "K"],   
                ["K", "K", "K", "K", "K", "K", "K", "K"]   
                ],
                "turn_color": "BANANA"
                }
            """.trimMargin()
        }
            .andDo { print() }
            .andExpect { status { isBadRequest() } }
            .andExpect {
                content {
                    contentType(MediaType.APPLICATION_JSON)
                }
            }
            .andExpect {
                jsonPath("$.message", Matchers.any(String::class.java))
            }
    }

    @Test
    fun `should return bad request in create game when positions is null`() {
        mockMvc.post("/chess/new-game") {
            param("start-by", "AI")
            param("level", "BEGINNER")
            contentType = MediaType.APPLICATION_JSON
            content = """{
                "turn_color": "WHITE"
                }
            """.trimMargin()
        }
            .andDo { print() }
            .andExpect { status { isBadRequest() } }
            .andExpect {
                content {
                    contentType(MediaType.APPLICATION_JSON)
                }
            }
            .andExpect {
                jsonPath("$.message", Matchers.any(String::class.java))
            }
    }

    @Test
    fun `should return success when in create game board request is null, start by player and level is intermediate`() {
        mockMvc.post("/chess/new-game") {
            param("start-by", "PLAYER")
            param("level", "INTERMEDIATE")
            contentType = MediaType.APPLICATION_JSON
        }
            .andDo { print() }
            .andExpect { status { isOk() } }
            .andExpect {
                content {
                    contentType(MediaType.APPLICATION_JSON)
                }
            }
            .andExpect {
                jsonPath(
                    "$.board_id",
                    Matchers.matchesPattern(Pattern.compile("([A-F0-9]{8}(-[A-F0-9]{4}){4}[A-F0-9]{8})"))
                )
                jsonPath("$.board.positions", Matchers.hasSize<List<Position>>(8))
                jsonPath("$.board.positions[0]", Matchers.hasSize<List<Position>>(8))
                jsonPath("$.board.positions[1]", Matchers.hasSize<List<Position>>(8))
                jsonPath("$.board.positions[2]", Matchers.hasSize<List<Position>>(8))
                jsonPath("$.board.positions[3]", Matchers.hasSize<List<Position>>(8))
                jsonPath("$.board.positions[4]", Matchers.hasSize<List<Position>>(8))
                jsonPath("$.board.positions[5]", Matchers.hasSize<List<Position>>(8))
                jsonPath("$.board.positions[6]", Matchers.hasSize<List<Position>>(8))
                jsonPath("$.board.positions[7]", Matchers.hasSize<List<Position>>(8))
                MockMvcResultMatchers.jsonPath("$.legal_movements").isArray
                MockMvcResultMatchers.jsonPath("$.ai_capture").doesNotHaveJsonPath()
                MockMvcResultMatchers.jsonPath("$.endgame").doesNotHaveJsonPath()
            }
    }

    @Test
    fun `should return success when in create game board request is null, start by ai and level is beginner`() {
        mockMvc.post("/chess/new-game") {
            param("start-by", "AI")
            param("level", "BEGINNER")
            contentType = MediaType.APPLICATION_JSON
        }
            .andDo { print() }
            .andExpect { status { isOk() } }
            .andExpect {
                content {
                    contentType(MediaType.APPLICATION_JSON)
                }
            }
            .andExpect {
                jsonPath(
                    "$.board_id",
                    Matchers.matchesPattern(Pattern.compile("([A-F0-9]{8}(-[A-F0-9]{4}){4}[A-F0-9]{8})"))
                )
                jsonPath("$.board.positions", Matchers.hasSize<List<Position>>(8))
                jsonPath("$.board.positions[0]", Matchers.hasSize<List<Position>>(8))
                jsonPath("$.board.positions[1]", Matchers.hasSize<List<Position>>(8))
                jsonPath("$.board.positions[2]", Matchers.hasSize<List<Position>>(8))
                jsonPath("$.board.positions[3]", Matchers.hasSize<List<Position>>(8))
                jsonPath("$.board.positions[4]", Matchers.hasSize<List<Position>>(8))
                jsonPath("$.board.positions[5]", Matchers.hasSize<List<Position>>(8))
                jsonPath("$.board.positions[6]", Matchers.hasSize<List<Position>>(8))
                jsonPath("$.board.positions[7]", Matchers.hasSize<List<Position>>(8))
                MockMvcResultMatchers.jsonPath("$.legal_movements").isArray
                MockMvcResultMatchers.jsonPath("$.ai_capture").isString
                MockMvcResultMatchers.jsonPath("$.endgame").doesNotHaveJsonPath()
            }
    }

    @Test
    fun `should return success when in create game board request is not null with turn_color white`() {
        mockMvc.post("/chess/new-game") {
            param("start-by", "AI")
            param("level", "BEGINNER")
            contentType = MediaType.APPLICATION_JSON
            content = """{
                "positions": [
                ["r", "n", "b", "q", "k", "b", "n", "r"],
                ["p", "p", "p", "p", "p", "p", "p", "p"],
                [" ", " ", " ", "g", " ", " ", " ", " "],   
                [" ", " ", " ", " ", " ", " ", " ", " "],   
                [" ", " ", " ", " ", " ", " ", " ", " "],   
                [" ", " ", " ", "G", " ", " ", " ", " "],   
                ["P", "P", "P", "P", "P", "P", "P", "P"],   
                ["R", "N", "B", "Q", "K", "B", "N", "R"]   
                ],
                "turn_color": "WHITE"
                }
            """.trimMargin()
        }
            .andDo { print() }
            .andExpect { status { isOk() } }
            .andExpect {
                content {
                    contentType(MediaType.APPLICATION_JSON)
                }
            }
            .andExpect {
                jsonPath(
                    "$.board_id",
                    Matchers.matchesPattern(Pattern.compile("([A-F0-9]{8}(-[A-F0-9]{4}){4}[A-F0-9]{8})"))
                )
                jsonPath("$.board.positions", Matchers.hasSize<List<Position>>(8))
                jsonPath("$.board.positions[0]", Matchers.hasSize<List<Position>>(8))
                jsonPath("$.board.positions[1]", Matchers.hasSize<List<Position>>(8))
                jsonPath("$.board.positions[2]", Matchers.hasSize<List<Position>>(8))
                jsonPath("$.board.positions[3]", Matchers.hasSize<List<Position>>(8))
                jsonPath("$.board.positions[4]", Matchers.hasSize<List<Position>>(8))
                jsonPath("$.board.positions[5]", Matchers.hasSize<List<Position>>(8))
                jsonPath("$.board.positions[6]", Matchers.hasSize<List<Position>>(8))
                jsonPath("$.board.positions[7]", Matchers.hasSize<List<Position>>(8))
                MockMvcResultMatchers.jsonPath("$.legal_movements").isArray
                MockMvcResultMatchers.jsonPath("$.ai_capture").isString
                MockMvcResultMatchers.jsonPath("$.endgame").doesNotHaveJsonPath()
            }
    }

    @Test
    fun `should return success when in create game board request is not null with turn_color black`() {
        mockMvc.post("/chess/new-game") {
            param("start-by", "PLAYER")
            param("level", "BEGINNER")
            contentType = MediaType.APPLICATION_JSON
            content = """{
                "positions": [
                ["r", "n", "b", "q", "k", "b", "n", "r"],
                ["p", "p", "p", "p", "p", "p", "p", "p"],
                [" ", " ", " ", "g", " ", " ", " ", " "],   
                [" ", " ", " ", " ", " ", " ", " ", " "],   
                [" ", " ", " ", " ", " ", " ", " ", " "],   
                [" ", " ", " ", "G", " ", " ", " ", " "],   
                ["P", "P", "P", "P", "P", "P", "P", "P"],   
                ["R", "N", "B", "Q", "K", "B", "N", "R"]   
                ],
                "turn_color": "WHITE"
                }
            """.trimMargin()
        }
            .andDo { print() }
            .andExpect { status { isOk() } }
            .andExpect {
                content {
                    contentType(MediaType.APPLICATION_JSON)
                }
            }
            .andExpect {
                jsonPath(
                    "$.board_id",
                    Matchers.matchesPattern(Pattern.compile("([A-F0-9]{8}(-[A-F0-9]{4}){4}[A-F0-9]{8})"))
                )
                jsonPath("$.board.positions", Matchers.hasSize<List<Position>>(8))
                jsonPath("$.board.positions[0]", Matchers.hasSize<List<Position>>(8))
                jsonPath("$.board.positions[1]", Matchers.hasSize<List<Position>>(8))
                jsonPath("$.board.positions[2]", Matchers.hasSize<List<Position>>(8))
                jsonPath("$.board.positions[3]", Matchers.hasSize<List<Position>>(8))
                jsonPath("$.board.positions[4]", Matchers.hasSize<List<Position>>(8))
                jsonPath("$.board.positions[5]", Matchers.hasSize<List<Position>>(8))
                jsonPath("$.board.positions[6]", Matchers.hasSize<List<Position>>(8))
                jsonPath("$.board.positions[7]", Matchers.hasSize<List<Position>>(8))
                MockMvcResultMatchers.jsonPath("$.legal_movements").isArray
                MockMvcResultMatchers.jsonPath("$.ai_capture").doesNotHaveJsonPath()
                MockMvcResultMatchers.jsonPath("$.endgame").doesNotHaveJsonPath()
            }
    }

    @Test
    fun `should return bad request in move piece when game is not found`() {
        mockMvc.post("/chess/move") {
            param("board-id", "banana")
            contentType = MediaType.APPLICATION_JSON
            content = """{
                "move": "a2a3"
                }
            """.trimMargin()
        }
            .andDo { print() }
            .andExpect { status { isBadRequest() } }
            .andExpect {
                content {
                    contentType(MediaType.APPLICATION_JSON)
                }
            }
            .andExpect {
                jsonPath("$.message", Matchers.any(String::class.java))
            }
    }

    @Test
    fun `should return bad request in move piece when move is null`() {

        val result = mockMvc.post("/chess/new-game") {
            param("start-by", "PLAYER")
            param("level", "BEGINNER")
            contentType = MediaType.APPLICATION_JSON
        }
            .andDo { print() }
            .andExpect { status { isOk() } }.andReturn()

        val boardId = JsonPath.read<String>(result.response.contentAsString, "$.board_id")

        mockMvc.post("/chess/move") {
            param("board-id", boardId)
            contentType = MediaType.APPLICATION_JSON
            content = """{
                }
            """.trimMargin()
        }
            .andDo { print() }
            .andExpect { status { isBadRequest() } }
            .andExpect {
                content {
                    contentType(MediaType.APPLICATION_JSON)
                }
            }
            .andExpect {
                jsonPath("$.message", Matchers.any(String::class.java))
            }
    }

    @Test
    fun `should return bad request in move piece when is an invalid move`() {

        val result = mockMvc.post("/chess/new-game") {
            param("start-by", "PLAYER")
            param("level", "BEGINNER")
            contentType = MediaType.APPLICATION_JSON
        }
            .andDo { print() }
            .andExpect { status { isOk() } }.andReturn()

        val boardId = JsonPath.read<String>(result.response.contentAsString, "$.board_id")

        mockMvc.post("/chess/move") {
            param("board-id", boardId)
            contentType = MediaType.APPLICATION_JSON
            content = """{
                "move": "a2a8"
                }
            """.trimMargin()
        }
            .andDo { print() }
            .andExpect { status { isBadRequest() } }
            .andExpect {
                content {
                    contentType(MediaType.APPLICATION_JSON)
                }
            }
            .andExpect {
                jsonPath("$.message", Matchers.any(String::class.java))
            }
    }

    @Test
    fun `should return success in move piece when is a valid move`() {

        val result = mockMvc.post("/chess/new-game") {
            param("start-by", "PLAYER")
            param("level", "BEGINNER")
            contentType = MediaType.APPLICATION_JSON
        }
            .andDo { print() }
            .andExpect { status { isOk() } }.andReturn()

        val boardId = JsonPath.read<String>(result.response.contentAsString, "$.board_id")

        mockMvc.post("/chess/move") {
            param("board-id", boardId)
            contentType = MediaType.APPLICATION_JSON
            content = """{
                "move": "a2a3"
                }
            """.trimMargin()
        }
            .andDo { print() }
            .andExpect { status { isOk() } }
            .andExpect {
                content {
                    contentType(MediaType.APPLICATION_JSON)
                }
            }
            .andExpect {
                jsonPath("$.board_id", Matchers.equalTo(boardId))
                jsonPath("$.board.positions", Matchers.hasSize<List<Position>>(8))
                jsonPath("$.board.positions[0]", Matchers.hasSize<List<Position>>(8))
                jsonPath("$.board.positions[1]", Matchers.hasSize<List<Position>>(8))
                jsonPath("$.board.positions[2]", Matchers.hasSize<List<Position>>(8))
                jsonPath("$.board.positions[3]", Matchers.hasSize<List<Position>>(8))
                jsonPath("$.board.positions[4]", Matchers.hasSize<List<Position>>(8))
                jsonPath("$.board.positions[5]", Matchers.hasSize<List<Position>>(8))
                jsonPath("$.board.positions[6]", Matchers.hasSize<List<Position>>(8))
                jsonPath("$.board.positions[7]", Matchers.hasSize<List<Position>>(8))
                MockMvcResultMatchers.jsonPath("$.legal_movements").isArray
                MockMvcResultMatchers.jsonPath("$.ai_capture").isString
                MockMvcResultMatchers.jsonPath("$.endgame").doesNotHaveJsonPath()
            }
    }

    @Test
    fun `should return bad request in surrender when game is not found`() {
        mockMvc.get("/chess/surrender") {
            param("board-id", "banana")
        }
            .andDo { print() }
            .andExpect { status { isBadRequest() } }
            .andExpect {
                content {
                    contentType(MediaType.APPLICATION_JSON)
                }
            }
            .andExpect {
                jsonPath("$.message", Matchers.any(String::class.java))
            }
    }

    @Test
    fun `should return success in surrender with black winning`() {

        val result = mockMvc.post("/chess/new-game") {
            param("start-by", "PLAYER")
            param("level", "BEGINNER")
            contentType = MediaType.APPLICATION_JSON
        }
            .andDo { print() }
            .andExpect { status { isOk() } }.andReturn()

        val boardId = JsonPath.read<String>(result.response.contentAsString, "$.board_id")

        mockMvc.get("/chess/surrender") {
            param("board-id", boardId)
        }
            .andDo { print() }
            .andExpect { status { isOk() } }
            .andExpect {
                content {
                    contentType(MediaType.APPLICATION_JSON)
                }
            }
            .andExpect {
                jsonPath("$.board_id", Matchers.equalTo(boardId))
                jsonPath("$.board.positions", Matchers.hasSize<List<Position>>(8))
                jsonPath("$.board.positions[0]", Matchers.hasSize<List<Position>>(8))
                jsonPath("$.board.positions[1]", Matchers.hasSize<List<Position>>(8))
                jsonPath("$.board.positions[2]", Matchers.hasSize<List<Position>>(8))
                jsonPath("$.board.positions[3]", Matchers.hasSize<List<Position>>(8))
                jsonPath("$.board.positions[4]", Matchers.hasSize<List<Position>>(8))
                jsonPath("$.board.positions[5]", Matchers.hasSize<List<Position>>(8))
                jsonPath("$.board.positions[6]", Matchers.hasSize<List<Position>>(8))
                jsonPath("$.board.positions[7]", Matchers.hasSize<List<Position>>(8))
                MockMvcResultMatchers.jsonPath("$.legal_movements").isArray
                MockMvcResultMatchers.jsonPath("$.ai_capture").doesNotHaveJsonPath()
                MockMvcResultMatchers.jsonPath("$.endgame").isNotEmpty
                jsonPath("$.endgame.winner", Matchers.equalTo(Color.BLACK.name))
                jsonPath("$.endgame.endgame_message", Matchers.equalTo(EndgameMessage.SURRENDER.message))
            }
    }

    @Test
    fun `should return success in surrender with white winning`() {

        val result = mockMvc.post("/chess/new-game") {
            param("start-by", "AI")
            param("level", "BEGINNER")
            contentType = MediaType.APPLICATION_JSON
        }
            .andDo { print() }
            .andExpect { status { isOk() } }.andReturn()

        val boardId = JsonPath.read<String>(result.response.contentAsString, "$.board_id")

        mockMvc.get("/chess/surrender") {
            param("board-id", boardId)
        }
            .andDo { print() }
            .andExpect { status { isOk() } }
            .andExpect {
                content {
                    contentType(MediaType.APPLICATION_JSON)
                }
            }
            .andExpect {
                jsonPath("$.board_id", Matchers.equalTo(boardId))
                jsonPath("$.board.positions", Matchers.hasSize<List<Position>>(8))
                jsonPath("$.board.positions[0]", Matchers.hasSize<List<Position>>(8))
                jsonPath("$.board.positions[1]", Matchers.hasSize<List<Position>>(8))
                jsonPath("$.board.positions[2]", Matchers.hasSize<List<Position>>(8))
                jsonPath("$.board.positions[3]", Matchers.hasSize<List<Position>>(8))
                jsonPath("$.board.positions[4]", Matchers.hasSize<List<Position>>(8))
                jsonPath("$.board.positions[5]", Matchers.hasSize<List<Position>>(8))
                jsonPath("$.board.positions[6]", Matchers.hasSize<List<Position>>(8))
                jsonPath("$.board.positions[7]", Matchers.hasSize<List<Position>>(8))
                MockMvcResultMatchers.jsonPath("$.legal_movements").isArray
                MockMvcResultMatchers.jsonPath("$.ai_capture").doesNotHaveJsonPath()
                MockMvcResultMatchers.jsonPath("$.endgame").isNotEmpty
                jsonPath("$.endgame.winner", Matchers.equalTo(Color.WHITE.name))
                jsonPath("$.endgame.endgame_message", Matchers.equalTo(EndgameMessage.SURRENDER.message))
            }
    }
}
