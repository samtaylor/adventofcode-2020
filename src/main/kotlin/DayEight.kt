object DayEight : Day<Int, Int>("/day-eight.txt") {

    override val partOneResult: Int
    get() = load().runUntilLoopOrEnd().accumulator

    override val partTwoResult: Int
    get() = load().runWithCorrection().accumulator

    private fun load() = Program(data.mapIndexed { index, instruction ->

        Instruction(index, when {

            instruction.contains("nop") -> NoOperation(instruction.substring(4).trim().toInt())
            instruction.contains("acc") -> Accumulator(instruction.substring(4).trim().toInt())
            instruction.contains("jmp") -> Jump(instruction.substring(4).trim().toInt())
            else -> throw RuntimeException("Unexpected instruction: $instruction")
        })
    })

    abstract class Operation
    data class Accumulator(val argument: Int) : Operation()
    data class Jump(val offset: Int) : Operation()
    data class NoOperation(val offset: Int) : Operation()
    data class Instruction(val index: Int, var operation: Operation, var executionCount: Int = 0)
    data class ProgramState(val accumulatorValue: Int, val instruction: Instruction)

    class Stack<T> {

        private var stackIndex = 0
        private val items = mutableListOf<T>()

        fun push(item: T) {

            items.add(item)
            stackIndex ++
        }

        fun pop(): T {

            stackIndex --
            val item = items[stackIndex]
            items.removeAt(stackIndex)
            return item
        }

        fun peak(): T = items[stackIndex - 1]

        override fun toString() = StringBuilder().apply {
            append("Stack:\n")
            items.forEachIndexed { index, item ->

                append("$index - $item\n")
            }
        }.toString()
    }

    class Program(private val instructions: List<Instruction>) {

        var accumulator = 0

        private var instructionPointer = 0
        private var currentInstruction = instructions[instructionPointer]
        private var firstRun = true
        private val stack = Stack<ProgramState>()

        fun runUntilLoopOrEnd() = apply {

            while (currentInstruction.executionCount == 0) {

                runInstruction(
                    instructionPointer,
                    accumulator,
                    currentInstruction,
                    if (firstRun) stack else null
                ) { newAccumulator, newPointer ->

                    accumulator = newAccumulator
                    instructionPointer = newPointer
                }

                if (instructionPointer < instructions.size) {

                    currentInstruction = instructions[instructionPointer]
                }
            }

            firstRun = false
        }

        fun runWithCorrection() = apply {

            while (running()) {

                runUntilLoopOrEnd()

                if (running()) {

                    popToPreviousNoOpOrJump().apply {

                        resetProgramToState()
                        flipInstruction()
                    }
                }
            }
        }

        private fun running() = instructionPointer < instructions.size

        private fun popToPreviousNoOpOrJump() = stack.apply {

            while (peak().instruction.operation is Accumulator) {

                pop()
            }
        }.pop()

        private fun ProgramState.resetProgramToState() {

            accumulator = accumulatorValue
            instructionPointer = instruction.index
            currentInstruction = instructions[instructionPointer]
            currentInstruction.executionCount = 0
        }

        private fun ProgramState.flipInstruction() {

            val instruction = instructions.find { it.index == instruction.index }
            when (val operation = instruction?.operation) {

                is NoOperation -> instruction.operation = Jump(operation.offset)
                is Jump -> instruction.operation = NoOperation(operation.offset)
            }

        }

        private fun runInstruction(instructionPointer: Int,
                                   accumulatorValue: Int,
                                   instruction: Instruction,
                                   stack: Stack<ProgramState>?,
                                   callback: (accumulatorValue: Int, instructionPointer: Int) -> Unit) {

            when (val operation = instruction.operation) {

                is NoOperation -> {

                    stack?.push(ProgramState(accumulatorValue, instruction))
                    callback(accumulatorValue, instructionPointer + 1)
                }
                is Accumulator -> {

                    stack?.push(ProgramState(accumulatorValue + operation.argument, instruction))
                    callback(accumulatorValue + operation.argument, instructionPointer + 1)
                }
                is Jump -> {

                    stack?.push(ProgramState(accumulatorValue, instruction))
                    callback(accumulatorValue, instructionPointer + operation.offset)
                }
            }

            instruction.executionCount ++
        }
    }
}