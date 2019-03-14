package ua.procamp;

public class Functions {
    /**
     * A static factory method that creates an integer function map with basic functions:
     * - abs (absolute value)
     * - sgn (signum function)
     * - increment
     * - decrement
     * - square
     *
     * @return an instance of {@link FunctionMap} that contains all listed functions
     */
    public static FunctionMap<Integer, Integer> intFunctionMap() {
        FunctionMap<Integer, Integer> intFunctionMap = new FunctionMap<>();

        intFunctionMap.addFunction("abs", x -> x * (x < 0 ? -1 : 1));
        intFunctionMap.addFunction("sgn", x -> x == 0 ? 0 : Math.abs(x)/x);
        intFunctionMap.addFunction("increment", x -> ++x);
        intFunctionMap.addFunction("decrement", x -> --x);
        intFunctionMap.addFunction("square", x -> x*x);

        return intFunctionMap;
    }
}
