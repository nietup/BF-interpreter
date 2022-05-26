import java.util.*

class InputHandler(private var input: String) {    
    fun getInput(): UByte {
        val result = input[0].toChar().code.toUByte()
        input = input.drop(1)
        return result
    }
}

class BrainLuck(private val code: String) {

    private val memory = Array<UByte>(20) { 0.toUByte() }
    private val loopStack = ArrayDeque<Int>()
    private var passCount = 0
    private var instructionPointer = 0
    private var memoryPointer = 0

    fun interpret(inputHandler: InputHandler): String {
        if (passCount > 0) {
            when (code[instructionPointer]) {
                '[' -> passCount++
                ']' -> passCount--
            }
            return ""
        }
        
        when (code[instructionPointer]) {
            '+' -> memory[memoryPointer]++
            '-' -> memory[memoryPointer]--
            '>' -> memoryPointer++
            '<' -> memoryPointer--
            '.' -> return memory[memoryPointer].toInt().toChar().toString()
            ',' -> memory[memoryPointer] = inputHandler.getInput()
            
            '[' -> 
            if (memory[memoryPointer] == 0.toUByte()) {
                passCount++
                if (loopStack.peek() == instructionPointer) {
                    loopStack.pop()
                }	
            }
            else {
                loopStack.push(instructionPointer)
            }
            
            ']' -> 
            if (memory[memoryPointer] > 0.toUByte()) {
                instructionPointer = loopStack.peek()
            }
            else {
                loopStack.pop()
            }
        }
        
        return ""
    }
    
	fun process(input: String): String {
       	val inputHandler = InputHandler(input)
        var result = ""

        while (instructionPointer < code.length) {
            result += interpret(inputHandler)
            instructionPointer++
        }
        
    	return result
  }
}
