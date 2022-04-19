package it.unibo.ai.didattica.competition.tablut.funsociety.client;

import it.unibo.ai.didattica.competition.tablut.client.TablutClient;
import it.unibo.ai.didattica.competition.tablut.domain.Action;
import it.unibo.ai.didattica.competition.tablut.domain.GameAshtonTablut;
import it.unibo.ai.didattica.competition.tablut.domain.State;
import it.unibo.ai.didattica.competition.tablut.domain.StateTablut;
import it.unibo.ai.didattica.competition.tablut.funsociety.search.IterativeDeepeningAlphaBeta;

import java.io.IOException;
import java.net.UnknownHostException;

public class FunSocietyClient extends TablutClient {

    private static final String playerName = "FunSociety";

    public FunSocietyClient(String player, String name, int timeout, String ipAddress) throws UnknownHostException, IOException {
        super(player, name, timeout, ipAddress);
    }

    public FunSocietyClient(String player, String name, int timeout) throws UnknownHostException, IOException {
        super(player, name, timeout);
    }

    public FunSocietyClient(String player, String name) throws UnknownHostException, IOException {
        super(player, name);
    }

    public FunSocietyClient(String player, String name, String ipAddress) throws UnknownHostException, IOException {
        super(player, name, ipAddress);
    }

    @Override
    public void run() {

        //All'inizio e' necessario comunicare il nome
        try {
            this.declareName();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        State state = new StateTablut();
        GameAshtonTablut game = new GameAshtonTablut(1, -1, "log", "white", "black");
        state.setTurn(State.Turn.WHITE);

        System.out.println("Stai giocando come player " + this.getPlayer().toString());

        while (true){
            try{
                this.read();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                System.exit(-2);
            }

            state = this.getCurrentState();

            if (this.getPlayer().equals(State.Turn.WHITE)){
                //Il nostro player e' white ed e' il nostro turno
                if (state.getTurn().equals(State.Turn.WHITE)){
                    IterativeDeepeningAlphaBeta search = new IterativeDeepeningAlphaBeta(game, Double.MIN_VALUE, Double.MAX_VALUE, this.timeout);
                    Action a = search.makeDecision(state);

                    try{
                        this.write(a);
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }

                } else if (state.getTurn().equals(State.Turn.WHITEWIN)){
                    System.out.println("Vittoria!");
                } else if (state.getTurn().equals(State.Turn.BLACKWIN)){
                    System.out.println("Sconfitta :(");
                } else if (state.getTurn().equals(State.Turn.DRAW)){
                    System.out.println("Pareggio :|");
                }
            }  else if (this.getPlayer().equals(State.Turn.BLACK)){
                //Il nostro player e' black ed e' il nostro turno
                if (state.getTurn().equals(State.Turn.BLACK)){
                    IterativeDeepeningAlphaBeta search = new IterativeDeepeningAlphaBeta(game, Double.MIN_VALUE, Double.MAX_VALUE, this.timeout);
                    Action a = search.makeDecision(state);

                    try{
                        this.write(a);
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                } else if (state.getTurn().equals(State.Turn.WHITEWIN)){
                    System.out.println("Sconfitta :(");
                } else if (state.getTurn().equals(State.Turn.BLACKWIN)){
                    System.out.println("Vittoria!");
                } else if (state.getTurn().equals(State.Turn.DRAW)){
                    System.out.println("Pareggio :|");
                }
            }

        }
    }

    public static void main(String[] args){

        int timeout = 60;
        String serverIP = "localhost";

        if (args.length < 1){ //Nessun argomento
            System.out.println("Devi specificare almeno il tipo di giocatore!");
            System.exit(-1);
        }

        String role = args[0].toLowerCase(); //white or black

        if (args.length > 1){
            try{
                timeout = Integer.parseInt(args[1]);
            } catch (NumberFormatException ex){
                System.out.println("Il timeout deve essere un intero!");
                System.exit(-1);
            }
        }

        if (args.length > 2){
            serverIP = args[2];
        }

        try{
            FunSocietyClient client = new FunSocietyClient(role, FunSocietyClient.playerName, timeout, serverIP);
            client.run();
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
