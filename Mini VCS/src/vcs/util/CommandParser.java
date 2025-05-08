package vcs.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Utility class for parsing command-line arguments for the version control system.
 */
public class CommandParser {
    
    /**
     * Parse command-line arguments into a command and its options.
     *
     * @param args Command-line arguments
     * @return CommandResult containing the command and its options
     */
    public static CommandResult parseCommand(String[] args) {
        if (args.length == 0) {
            return new CommandResult("help", new HashMap<>(), new ArrayList<>());
        }
        
        String command = args[0];
        Map<String, String> options = new HashMap<>();
        List<String> parameters = new ArrayList<>();
        
        for (int i = 1; i < args.length; i++) {
            String arg = args[i];
            
            if (arg.startsWith("--")) {
                // Long form option (--option=value or --option value)
                String optionName = arg.substring(2);
                String optionValue = "true";  // Default value if no value is provided
                
                if (optionName.contains("=")) {
                    String[] parts = optionName.split("=", 2);
                    optionName = parts[0];
                    optionValue = parts[1];
                } else if (i + 1 < args.length && !args[i + 1].startsWith("-")) {
                    // Next argument is the value
                    optionValue = args[i + 1];
                    i++;  // Skip the next argument since it's the value
                }
                
                options.put(optionName, optionValue);
            } else if (arg.startsWith("-")) {
                // Short form option (-o value or -o)
                String optionName = arg.substring(1);
                String optionValue = "true";  // Default value if no value is provided
                
                if (i + 1 < args.length && !args[i + 1].startsWith("-")) {
                    // Next argument is the value
                    optionValue = args[i + 1];
                    i++;  // Skip the next argument since it's the value
                }
                
                options.put(optionName, optionValue);
            } else {
                // Not an option, must be a parameter
                parameters.add(arg);
            }
        }
        
        return new CommandResult(command, options, parameters);
    }
    
    /**
     * Class to hold the result of parsing a command.
     */
    public static class CommandResult {
        private final String command;
        private final Map<String, String> options;
        private final List<String> parameters;
        
        public CommandResult(String command, Map<String, String> options, List<String> parameters) {
            this.command = command;
            this.options = options;
            this.parameters = parameters;
        }
        
        /**
         * Gets the command name.
         *
         * @return Command name
         */
        public String getCommand() {
            return command;
        }
        
        /**
         * Gets all options.
         *
         * @return Map of option names to values
         */
        public Map<String, String> getOptions() {
            return options;
        }
        
        /**
         * Checks if an option is present.
         *
         * @param option Option name
         * @return true if the option is present, false otherwise
         */
        public boolean hasOption(String option) {
            return options.containsKey(option);
        }
        
        /**
         * Gets the value of an option.
         *
         * @param option Option name
         * @return Option value or null if not present
         */
        public String getOption(String option) {
            return options.get(option);
        }
        
        /**
         * Gets the value of an option with a default value if not present.
         *
         * @param option Option name
         * @param defaultValue Default value to return if option is not present
         * @return Option value or default value if not present
         */
        public String getOption(String option, String defaultValue) {
            return options.getOrDefault(option, defaultValue);
        }
        
        /**
         * Gets all parameters.
         *
         * @return List of parameters
         */
        public List<String> getParameters() {
            return parameters;
        }
        
        /**
         * Gets a parameter at a specific index.
         *
         * @param index Parameter index
         * @return Parameter value or null if index is out of bounds
         */
        public String getParameter(int index) {
            if (index >= 0 && index < parameters.size()) {
                return parameters.get(index);
            }
            return null;
        }
        
        /**
         * Gets a parameter at a specific index with a default value if not present.
         *
         * @param index Parameter index
         * @param defaultValue Default value to return if index is out of bounds
         * @return Parameter value or default value if index is out of bounds
         */
        public String getParameter(int index, String defaultValue) {
            if (index >= 0 && index < parameters.size()) {
                return parameters.get(index);
            }
            return defaultValue;
        }
        
        /**
         * Gets the number of parameters.
         *
         * @return Number of parameters
         */
        public int getParameterCount() {
            return parameters.size();
        }
    }
    
    /**
     * Formats help text for a command with proper alignment.
     *
     * @param command Command name
     * @param description Command description
     * @param options Map of option names to descriptions
     * @return Formatted help text
     */
    public static String formatHelpText(String command, String description, Map<String, String> options) {
        StringBuilder sb = new StringBuilder();
        
        sb.append("Command: ").append(command).append("\n");
        sb.append("Description: ").append(description).append("\n\n");
        
        if (!options.isEmpty()) {
            sb.append("Options:\n");
            
            // Find the longest option name for alignment
            int maxLength = 0;
            for (String option : options.keySet()) {
                maxLength = Math.max(maxLength, option.length());
            }
            
            // Format each option
            for (Map.Entry<String, String> entry : options.entrySet()) {
                String option = entry.getKey();
                String optionDesc = entry.getValue();
                
                // Pad the option name for alignment
                sb.append("  ").append(option);
                for (int i = option.length(); i < maxLength + 2; i++) {
                    sb.append(" ");
                }
                
                sb.append(optionDesc).append("\n");
            }
        }
        
        return sb.toString();
    }
}
