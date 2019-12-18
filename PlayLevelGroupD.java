package groupD;

import agents.MarioAgent;
import engine.core.*;
import levelGenerators.MarioLevelGenerator;
import groupD.GeneratorGroupD;
import java.util.*;

import java.io.IOException;

import static engine.helper.RunUtils.*;

@SuppressWarnings("ConstantConditions")
public class PlayLevelGroupD {

    static GeneratorGroupD ours = new GeneratorGroupD();

    static String[] gen_levels = {
            "src/groupD/generated_level1.txt",
            "src/groupD/generated_level2.txt",
            "src/groupD/generated_level3.txt",
            "src/groupD/generated_level4.txt",
            "src/groupD/generated_level5.txt",
            "src/groupD/generated_level6.txt",
            "src/groupD/generated_level7.txt",
            "src/groupD/generated_level8.txt",
            "src/groupD/generated_level9.txt",
            "src/groupD/generated_level10.txt"
    };

    static int[]  gen_fitness = {
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0
    };

    public static int get_fittest(int[] a){
        int max = a[0];
        int index = 0;

        for (int i = 0; i < a.length; i++) {
            if (max < a[i]){
                max = a[i];
                index = i;
            }
        }
        return index;
    }

    public static void main(String[] args) throws IOException {
        // Run settings:
        boolean visuals = false;  // Set to false if no visuals required for this run.
        boolean generateDifferentLevels = false;  // If true, each play will be a different generated level.
//        String levelFile = "levels/ore/lvl-1001.txt";  // null
        String levelFile = "src/groupD/generated_level1.txt";  // null;
        MarioLevelGenerator generator = new levelGenerators.notch.LevelGenerator();  // null;

        ours.runGenerator("src/groupD/generated_level1.txt");
        ours.runGenerator("src/groupD/generated_level2.txt");
        ours.runGenerator("src/groupD/generated_level3.txt");
        ours.runGenerator("src/groupD/generated_level4.txt");
        ours.runGenerator("src/groupD/generated_level5.txt");
        ours.runGenerator("src/groupD/generated_level6.txt");
        ours.runGenerator("src/groupD/generated_level7.txt");
        ours.runGenerator("src/groupD/generated_level8.txt");
        ours.runGenerator("src/groupD/generated_level9.txt");
        ours.runGenerator("src/groupD/generated_level10.txt");


        // Note: either levelFile or generator must be non-null. If neither is null, levelFile takes priority.
        if (levelFile == null && generator == null) {
            return;
        }

        // Create a MarioGame instance and game-playing AI
        MarioGame game = new MarioGame();
        MarioAgent agent = new agents.robinBaumgarten.Agent();

        for(int i = 0; i < 10; i ++){
            String level = getLevel(gen_levels[i], generator);
            MarioResult result = game.runGame(agent, level, 20, 0, visuals);
//            System.out.println(result.getGameStatus().toString());
            if(result.getGameStatus().toString() == "WIN"){
                gen_fitness[i] += 50;
            }
            gen_fitness[i] += result.getCurrentCoins() + result.getKillsByFall() + result.getKillsByFire() +
                    result.getKillsByShell() + result.getKillsByStomp() + result.getKillsTotal() +
                    result.getMaxJumpAirTime() + result.getNumBumpBrick() +
                    result.getNumBumpQuestionBlock() + result.getNumCollectedFireflower() +
                    result.getNumCollectedMushrooms() + result.getNumJumps() +
                    result.getNumCollectedTileCoins() + result.getMaxXJump();
//            System.out.println(gen_fitness[i]);
        }
        int r2 = new Random().nextInt(10);
        int r3 = new Random().nextInt(10);

        ours.runRandomGenerator(gen_levels[get_fittest(gen_fitness)],
                gen_levels[get_fittest(gen_fitness)],
                gen_levels[r2], gen_levels[r3]);

        levelFile = gen_levels[get_fittest(gen_fitness)];
        // Grab a level from file, found in directory "levels/" or pass null to generate a level automatically.
        String level = getLevel(levelFile, generator);

        // Display the entire level.
        game.buildWorld(level, 1);
        visuals = true;
        // Repeat the game several times, maybe.
        int playAgain = 0;
        while (playAgain == 0) {  // 0 - play again! 1 - end execution.

            // Play the level, either as a human ...
//            MarioResult result = game.playGame(level, 200, 0);

            // ... Or with an AI agent
            MarioResult result = game.runGame(agent, level, 20, 0, visuals);

            // Print the results of the game
            System.out.println(result.getGameStatus().toString());
//            System.out.println(resultToStats(result).toString());

//            if (generateDifferentLevels) {
//                level = generateLevel(generator);
//            }

            // Check if we should play again.
            playAgain = (game.playAgain == 0 && visuals) ? 0 : 1;  // If visuals are not on, only play 1 time
        }
    }
}
