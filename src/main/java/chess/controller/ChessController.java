package chess.controller;

import chess.controller.view.BoardView;
import chess.dto.LogInDto;
import chess.service.ChessGameService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ChessController {
    private final ChessGameService chessGameService;

    public ChessController(ChessGameService chessGameService) {
        this.chessGameService = chessGameService;
    }

    @GetMapping("/")
    public ModelAndView index(@RequestParam(required = false, defaultValue = "") String msg) {
        return getIndexModel(msg);
    }

    @PostMapping("/exit")
    public String deleteGame(@RequestParam String gameId, @RequestParam String gamePassword) {
        chessGameService.validateEnd(gameId);
        chessGameService.cleanGame(new LogInDto(gameId, gamePassword));
        return "redirect:/";
    }

    @GetMapping("/exit")
    public String exitAndDeleteGame(@RequestParam String gameId) {
        chessGameService.cleanGame(gameId);
        return "redirect:/";
    }

    @PostMapping("/init")
    public String initGame(@ModelAttribute LogInDto logInDto) {
        chessGameService.createGame(logInDto);
        return "redirect:/game/" + logInDto.getGameId();
    }

    @PostMapping("/start")
    public String startGame(@ModelAttribute LogInDto logInDto) {
        chessGameService.validateLogIn(logInDto);
        return "redirect:/game/" + logInDto.getGameId();
    }

    @GetMapping(path = "/game/{gameId}")
    public ModelAndView getGame(@PathVariable String gameId) {
        return getGameModel(gameId);
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView exception(Exception e) {
        return getIndexModel(e.getMessage());
    }

    private ModelAndView getIndexModel(String msg) {
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("msg", msg);
        modelAndView.addObject("rooms", chessGameService.getRooms());
        return modelAndView;
    }

    private ModelAndView getGameModel(String gameId) {
        ModelAndView modelAndView = new ModelAndView("game");
        modelAndView.addObject("pieces", BoardView.of(chessGameService.getCurrentGame(gameId)).getBoardView());
        modelAndView.addObject("gameId", gameId);
        modelAndView.addObject("status", chessGameService.calculateGameResult(gameId));
        return modelAndView;
    }
}
