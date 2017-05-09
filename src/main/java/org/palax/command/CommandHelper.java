package org.palax.command;

import org.apache.log4j.Logger;
import org.palax.command.admin.*;
import org.palax.command.auth.LoginCommand;
import org.palax.command.auth.LogoutCommand;
import org.palax.command.auth.RegistrationCommand;
import org.palax.command.info.CompleteTestInfoCommand;
import org.palax.command.info.TestInfoCommand;
import org.palax.command.profile.ChangeUserPasswdCommand;
import org.palax.command.profile.UpdateUserCommand;
import org.palax.command.redirect.CreateCategoryRedirectCommand;
import org.palax.command.redirect.CreateTestRedirectCommand;
import org.palax.command.redirect.ProfileRedirectCommand;
import org.palax.command.redirect.RegistrationRedirectCommand;
import org.palax.command.student.GetAllTestCommand;
import org.palax.command.student.StartTestCommand;
import org.palax.command.student.SubmitTestCommand;
import org.palax.command.student.TestHistoryCommand;
import org.palax.command.tutor.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * The {@code CommandHelper} a class which, depending on the query parameter
 * and selects the page to redirect
 *
 * @author Taras Palashynskyy
 */

public class CommandHelper {
    /**Object for logging represent by {@link Logger}. */
    private static final Logger logger = Logger.getLogger(CommandHelper.class);

    /**Singleton object which is returned when you try to create a new instance */
    private static volatile CommandHelper commandHelper;
    /**Map that stores the string representation of commands and their instance */
    private final Map<String, Command> commandMapping;

    /**
     * Constructor which initialize {@code commandMapping}
     */
    private CommandHelper() {
        logger.debug("Initialization command mapping");
        commandMapping = new HashMap<>();
        commandMapping.put("LOGIN", new LoginCommand());
        commandMapping.put("LOGOUT", new LogoutCommand());
        commandMapping.put("REGISTRATION", new RegistrationCommand());
        commandMapping.put("REDIRECT-REGISTRATION", new RegistrationRedirectCommand());
        commandMapping.put("REDIRECT-PROFILE", new ProfileRedirectCommand());
        commandMapping.put("CHANGE-PASSWORD", new ChangeUserPasswdCommand());
        commandMapping.put("UPDATE-PROFILE", new UpdateUserCommand());
        commandMapping.put("USERS", new GetAllUserCommand());
        commandMapping.put("FIND-USER", new FindUserCommand());
        commandMapping.put("USER-INFO", new UserInfoCommand());
        commandMapping.put("CHANGE-ROLE", new ChangeRoleCommand());
        commandMapping.put("DELETE-USER", new DeleteUserCommand());
        commandMapping.put("COMPLETE-TEST-INFO", new CompleteTestInfoCommand());
        commandMapping.put("TEST-HISTORY", new TestHistoryCommand());
        commandMapping.put("TEST-INFO", new TestInfoCommand());
        commandMapping.put("UPDATE-TEST", new UpdateTestCommand());
        commandMapping.put("ACTIVATE-TEST", new ActivateTestCommand());
        commandMapping.put("DEACTIVATE-TEST", new DeactivateTestCommand());
        commandMapping.put("TUTOR-TEST", new GetTutorTestCommand());
        commandMapping.put("CREATE-TEST", new CreateTestCommand());
        commandMapping.put("REDIRECT-CREATE-TEST", new CreateTestRedirectCommand());
        commandMapping.put("TESTS", new GetAllTestCommand());
        commandMapping.put("QUESTIONS", new GetTestQuestionsCommand());
        commandMapping.put("ADD-QUESTION", new AddQuestionCommand());
        commandMapping.put("UPDATE-QUESTION", new UpdateQuestionCommand());
        commandMapping.put("DELETE-QUESTION", new DeleteQuestionCommand());
        commandMapping.put("QUESTION-INFO", new GetQuestionAnswersCommand());
        commandMapping.put("ADD-ANSWER", new AddAnswerCommand());
        commandMapping.put("UPDATE-ANSWERS", new UpdateAnswersCommand());
        commandMapping.put("DELETE-ANSWER", new DeleteAnswerCommand());
        commandMapping.put("START-TEST", new StartTestCommand());
        commandMapping.put("SUBMIT-TEST", new SubmitTestCommand());
        commandMapping.put("REDIRECT-CREATE-CATEGORY", new CreateCategoryRedirectCommand());
        commandMapping.put("CREATE-CATEGORY", new CreateCategoryCommand());
        commandMapping.put("CATEGORIES", new GetAllCategoryCommand());
        commandMapping.put("UPDATE-CATEGORY", new UpdateCategoryCommand());
        commandMapping.put("DELETE-CATEGORY", new DeleteCategoryCommand());
    }

    /**
     * Always return same {@link CommandHelper} instance
     *
     * @return always return same {@link CommandHelper} instance
     */
    public static CommandHelper getInstance() {
        CommandHelper localInstance = commandHelper;
        if(localInstance == null) {
            synchronized (CommandHelper.class) {
                localInstance = commandHelper;
                if(localInstance == null) {
                    commandHelper = new CommandHelper();
                    logger.debug("Create first CommandHelper instance");
                }
            }
        }
        logger.debug("Return CommandHelper instance");
        return commandHelper;
    }

    /**
     *
     *
     * @param request {@code request} must contain a {@code "command"} parameter
     * @return return {@link Command} instance which corresponds to the transmitted command
     */
    public Command getCommand(HttpServletRequest request) {
        Command current = new EmptyCommand();

        String action = request.getParameter("command");
        if (action == null || action.isEmpty()) {
            logger.info("The request doesn't pass command " + request.getMethod() + " " + request.getRequestURI());
            return current;
        }

        logger.info("The request pass command " + request.getMethod() + " " + action.toUpperCase());
        current = commandMapping.get(action.toUpperCase());

        return current;
    }

}
