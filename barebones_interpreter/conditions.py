class WhileCondition:
    def __init__(self, line_no, variable, value, is_not=False):
        self.line_no = line_no + 1
        self.variable = variable
        self.value = int(value)
        self.is_not = is_not

    def is_met(self, variables):
        return (self.is_not and variables[self.variable] != self.value) or (not self.is_not and variables[self.variable] == self.value)