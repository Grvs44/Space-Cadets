#! /usr/bin/env python3
from tkinter import *
from tkinter.filedialog import askopenfilename, asksaveasfile, asksaveasfilename
from tkinter.messagebox import showinfo
from pathlib import Path
from conditions import *

class Interpreter(Tk):
    def __init__(self, filename=None):
        super().__init__()
        self.grid_rowconfigure(1, weight=1)
        self.grid_columnconfigure(1, weight=1)

        menubar = Menu(self)
        runmenu = Menu(menubar, tearoff=0, bg='#fff')
        runmenu.add_command(label='Run', command=self.run_code)
        runmenu.add_command(label='Run with Java interpreter')

        savemenu = Menu(menubar, tearoff=0, bg='#fff')
        savemenu.add_command(label='Save', command=self.save_check)
        savemenu.add_command(label='Save as', command=self.save_as)

        openmenu = Menu(menubar, tearoff=0, bg='#fff')
        openmenu.add_command(label='Open', command=self.open)
        openmenu.add_command(label='Open in new window', command=self.open_window)

        newmenu = Menu(menubar, tearoff=0, bg='#fff')
        newmenu.add_command(label='New window', command=Interpreter)
        newmenu.add_command(label='Clear', command=self.clear)

        menubar.add_cascade(label='Run', menu=runmenu)
        menubar.add_cascade(label='Save', menu=savemenu)
        menubar.add_cascade(label='Open', menu=openmenu)
        menubar.add_cascade(label='New', menu=newmenu)
        self.config(menu=menubar)

        self.codebox = Text(self, wrap='none')
        scroll_y = Scrollbar(self, command=self.codebox.yview)
        scroll_x = Scrollbar(self, command=self.codebox.xview, orient='horizontal')
        self.codebox.config(yscrollcommand=scroll_y.set, xscrollcommand=scroll_x.set)

        self.codebox.grid(row=1, column=1, sticky='NSEW')
        scroll_y.grid(row=1, column=2, sticky="NSEW")
        scroll_x.grid(row=2, column=1, sticky='NSEW')

        self.filedialogoptions = {'parent':self, 'defaultextension':'.txt', 'filetypes':[('Text file', '*.txt'), ('All files', '*')]}
        if filename:
            self.filename = filename
            self.filedialogoptions['initialdir'] = Path(filename).parent
            self.title(filename + ' - Barebones Editor')
            self.open_file()
        else:
            self.filename = None
            self.filedialogoptions['initialdir'] = Path.home()
            self.title('Barebones Editor')

    def clear(self):
        self.codebox.delete('0.0', END)

    def open(self):
        filename = askopenfilename(title='Open', **self.filedialogoptions)
        if len(filename):
            self.filename = filename
            self.open_file()
    
    def open_file(self):
        self.codebox.delete('0.0', END)
        file = open(self.filename)
        self.codebox.insert('0.0', file.read())
        file.close()
        self.filedialogoptions['initialdir'] = Path(self.filename).parent

    def open_window(self):
        filename = askopenfilename(title='Open in new window', **self.filedialogoptions)
        if len(filename):
            Interpreter(filename)

    def run_code(self):
        RunWindow(self)

    def save(self):
        file = open(self.filename, 'w')
        file.write(self.codebox.get('0.0', END).rstrip() + '\n')
        file.close()
        self.filedialogoptions['initialdir'] = Path(self.filename).parent
        showinfo('Barebones Interpreter', 'Saved', parent=self)
    
    def save_as(self):
        filename = asksaveasfilename(title='Save as', **self.filedialogoptions)
        if len(filename):
            self.filename = filename
            self.save()

    def save_check(self):
        if self.filename:
            self.save()
        else:
            self.save_as()

class RunWindow(Toplevel):
    def __init__(self, parent):
        super().__init__()
        self.title('Barebones Interpreter')
        self.grid_rowconfigure(1, weight=1)
        self.grid_columnconfigure(1, weight=1)

        menubar = Menu(self)
        menubar.add_command(label='Run', command=self.run_lines)
        menubar.add_command(label='Step', command=self.step)
        menubar.add_command(label='Save output', command=self.save)
        self.config(menu=menubar)

        self.varbox = Text(self)
        scroll = Scrollbar(self, command=self.varbox.yview)
        self.varbox.config(yscrollcommand=scroll.set)
        self.varbox.insert('0.0', 'Click "Run" to execute the entire program, or "Step" for one line at a time')

        self.varbox.grid(row=1, column=1, sticky='NSEW')
        scroll.grid(row=1, column=2, sticky="NSEW")

        self.codebox = parent.codebox
        self.line_no = 0
        self.finished = True
    
    def add(self, text):
        self.varbox.insert(END, text)
        self.varbox.yview(END)

    def log_variables(self):
        output = '''
## Variables on line %i `%s`
|Variable|Value|
|:------:|:---:|
''' % (self.line_no + 1, self.code[self.line_no].strip())
        for var in self.variables:
            output += '|%s|%i|\n' % (var, self.variables[var])
        self.add(output)

    def run_lines(self, count=None):
        if self.finished:
            self.variables = {}
            self.conditions = []
            self.code = self.codebox.get('0.0',END).rstrip().split(';')
            self.varbox.delete('0.0', END)
            self.finished = False
        error = None
        while self.line_no < len(self.code):
            line = self.code[self.line_no].strip().split()
            if len(line) == 0:
                self.line_no += 1
                continue
            elif line[0] == 'clear':
                self.variables[line[1]] = 0
            elif line[0] == 'incr':
                try:
                    self.variables[line[1]] += 1
                except KeyError:
                    error = 'Variable %s doesn\'t exist' % line[1]
            elif line[0] == 'decr':
                try:
                    self.variables[line[1]] -= 1
                except KeyError:
                    error = 'Variable %s doesn\'t exist' % line[1]
            elif line[0] == 'while' and line[-1] == 'do':
                if line[1] in self.variables:
                    if line[2].isnumeric():
                        self.conditions.append(WhileCondition(self.line_no, line[1], line[2]))
                    elif line[2] == 'not' and line[3].isnumeric():
                        self.conditions.append(WhileCondition(self.line_no, line[1], line[3], True))
                    else:
                        error = 'Badly formed while loop'
                else:
                    error = 'Variable %s doesn\'t exist' % line[1]
            elif line[0] == 'end':
                if len(self.conditions) == 0:
                    error = 'No loop to end'
                else:
                    condition = self.conditions[-1]
                    if condition.is_met(self.variables):
                        self.line_no = condition.line_no
                        continue
                    else:
                        self.conditions.pop()
            else:
                error = 'Command %s doesn\'t exist' % line[0]
            self.log_variables()
            self.line_no += 1
            if error:
                self.add('Error on line %i\t%s' % (self.line_no, error))
                self.finished = True
                self.line_no = 0
                break
            elif count:
                count -= 1
                if count == 0: break
        if self.line_no == len(self.code):
            self.add('Program completed')
            self.finished = True
            self.line_no = 0

    def step(self):
        self.run_lines(1)

    def save(self):
        file = asksaveasfile(title='Save as', parent=self, defaultextension='.md', filetypes=[('Markdown', '*.md'), ('Text file', '*.txt'), ('All files', '*')])
        if file:
            file.write(self.varbox.get('0.0', END).rstrip())
            file.close()
            showinfo('Barebones Interpreter', 'Saved output', parent=self)

def main():
    Interpreter().mainloop()

if __name__ == '__main__':
    main()
