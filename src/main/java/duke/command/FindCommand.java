package duke.command;

import duke.Storage;
import duke.Task;
import duke.TaskList;
import duke.UserInterface;
import duke.exception.DukeListException;

import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * FindCommand class to execute find command when given by user.
 * @author Kor Ming Soon
 */
public class FindCommand extends Command {

    private String wordToFind;

    /**
     * Constructor for FindCommand
     * @param wordToFind word give by user to filter out relevant tasks.
     */
    public FindCommand(String wordToFind) {
        this.wordToFind = wordToFind;
    }

    /**
     * Execution command to begin search for with relevant word given.
     * @param tasklist list of tasks to be referenced from.
     * @param ui UserInterface for the command to prompt.
     * @return a filtered list according to the user input
     * @throws DukeListException When the input index does not match the list.
     */
    @Override
    public String execute(TaskList tasklist, UserInterface ui) throws DukeListException {

        List<Task> wholeTaskList = tasklist.getTaskList();

        BiFunction<Integer, String, Boolean> filterFunction = (index, filterWord) -> {
            return wholeTaskList.get(index).getTask().contains(filterWord);
        };
        
        String response = ui.listTask();

        response += IntStream.range(0, wholeTaskList.size()).filter( index -> {
            return filterFunction.apply(index, wordToFind);
        }).mapToObj(filteredIndex -> ui.printTask(filteredIndex + 1,
                wholeTaskList.get(filteredIndex).toString())).collect(Collectors.joining(""));

        if (response.equals("")) {
            throw new DukeListException("Your search result yields nothing.");
        }
      
        assert !response.equals("");
      
        return response;

    }
}
