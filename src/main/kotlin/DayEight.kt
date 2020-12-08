import IO.readFile

sealed class Operation
data class Accumulator(val argument: Int) : Operation()
data class Jump(val offset: Int) : Operation()
object NoOperation : Operation()

data class Instruction(val operation: Operation, var executionCount: Int = 0)
class Program(val instructions: List<Instruction>) {

    fun run(): Int {

        var accumulator = 0
        var instructionPointer = 0
        var currentInstruction = instructions[instructionPointer]
        while (currentInstruction.executionCount == 0) {

            when (val operation = currentInstruction.operation) {

                is NoOperation -> {

                    println("$instructionPointer - $operation")
                    instructionPointer ++
                }
                is Accumulator -> {

                    println("$instructionPointer - $operation")
                    accumulator += operation.argument
                    instructionPointer ++
                }
                is Jump -> {

                    println("$instructionPointer - $operation")
                    instructionPointer += operation.offset
                }
            }

            currentInstruction.executionCount ++
            currentInstruction = instructions[instructionPointer]
        }

        return accumulator
    }
}

object DayEight : Day<Int, Int> {

    override val filename = "/day-eight.txt"

    override val partOneResult: Int
        get() {

            return load().run()
        }
    override val partTwoResult: Int
        get() = TODO("Not yet implemented")

    private fun load() = Program(filename.readFile().map { instruction ->

        Instruction(when {

            instruction.contains("nop") -> NoOperation
            instruction.contains("acc") -> Accumulator(instruction.substring(4).trim().toInt())
            instruction.contains("jmp") -> Jump(instruction.substring(4).trim().toInt())
            else -> throw RuntimeException("Unexpected instruction: $instruction")
        })
    })
}