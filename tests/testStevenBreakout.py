import unittest
import javalang
import sys
import glob
sys.path.append("..") # Adds higher directory to python modules path.
from parseJava import CallGraph

class TestStevenBreakoutMethods(unittest.TestCase):

    """ Ran once before all tests. Used to set up the graph_dict on all java files in the StevenBreakout directory."""
    @classmethod
    def setUpClass(cls):
        call_graph = CallGraph()
        java_classes = []
        for filename in glob.glob('../StevenBreakout/**/*.java', recursive = True):
            print(f'Parsing {filename}')

            with open(filename) as java_file:
                java_code = java_file.read()
                try:
                    tree = javalang.parse.parse(java_code)  # A CompilationUnit (root of AST)
                    java_classes.extend(tree.types)
                except Exception as e:
                    print("Error {} occured".format(e))
                    raise

        cls.graph_dict = call_graph.create_defined_methods_and_fields_dict(java_classes)

        for java_class in java_classes:
            declarations_list = java_class.body  # The declarations in each class as a list
            call_graph.construct_class_dict(declarations_list, java_class.name, cls.graph_dict)  # TODO: Rename method



    # -------------------------------- Ball Tests ----------------------------------------------
    def test_Ball_getDy(self):
        expected_methods = set()
        actual_methods = self.graph_dict['Ball']['called_methods']['Ball.getDy']
        self.assertEqual(actual_methods, expected_methods)

    def test_Ball_setDxPositive(self):
        expected_methods = {'Ball.setDx', 'Ball.abs', 'Ball.getDx'}
        actual_methods = self.graph_dict['Ball']['called_methods']['Ball.setDxPositive']
        self.assertEqual(actual_methods, expected_methods)

    def test_Ball_move(self):
        expected_methods = {'Ball.pause', 'Ball.move'}
        actual_methods = self.graph_dict['Ball']['called_methods']['Ball.move']
        self.assertEqual(actual_methods, expected_methods)


    # -------------------------------- Bar Tests ----------------------------------------------
    def test_Bar_toString(self):
        expected_methods = set()
        actual_methods = self.graph_dict['Bar']['called_methods']['Bar.toString']
        self.assertEqual(actual_methods, expected_methods)

    def test_Bar_moveLeft(self):
        expected_methods = {'Ball.move'}
        actual_methods = self.graph_dict['Bar']['called_methods']['Bar.moveLeft']


    # -------------------------------- BreakoutProgram Tests ----------------------------------------------
    def test_BreakoutProgram_checkTopWall(self):
        expected_methods = {'Ball.setDyPositive', 'Bar.setY', 'Ball.setY', 'Brick.setY', 'BreakoutProgram.getY'}
        actual_methods = self.graph_dict['BreakoutProgram']['called_methods']['BreakoutProgram.checkTopWall']
        self.assertEqual(actual_methods, expected_methods)

    def test_BreakoutProgram_checkRun(self):
        expected_methods = {'BreakoutProgram.setColor', 'Ball.move', 'BreakoutProgram.checkBottomWall',
                            'BreakoutProgram.checkBrickWall', 'BrickWall.getBricksRemaining', 'BreakoutProgram.getHeight',
                            'BreakoutProgram.checkPaddle', 'BreakoutProgram.checkRightWall', 'Ball.startBall',
                            'BreakoutProgram.add', 'BreakoutProgram.checkTopWall', 'BreakoutProgram.exit',
                            'BreakoutProgram.setLocation', 'BreakoutProgram.getWidth', 'BreakoutProgram.pause',
                            'BreakoutProgram.setLabel', 'BreakoutProgram.remove', 'BreakoutProgram.setFont',
                            'BreakoutProgram.checkLeftWall'}
        actual_methods = self.graph_dict['BreakoutProgram']['called_methods']['BreakoutProgram.run']
        self.assertEqual(actual_methods, expected_methods)

    def test_BreakoutProgram_switchDirect(self):
        expected_methods = {'BreakoutProgram.switchDirectOneCorner', 'Ball.switchDx',
                            'Ball.switchDy', 'BreakoutProgram.switchDirectTwoCorners'}
        actual_methods = self.graph_dict['BreakoutProgram']['called_methods']['BreakoutProgram.switchDirect']
        self.assertEqual(actual_methods, expected_methods)

    def test_BreakoutProgram_checkBottomWall(self):
        expected_methods = {'BreakoutProgram.getHeight', 'BreakoutProgram.getY'}
        actual_methods = self.graph_dict['BreakoutProgram']['called_methods']['BreakoutProgram.checkBottomWall']
        self.assertEqual(actual_methods, expected_methods)

    def test_BreakoutProgram_keyPressed(self):
        expected_methods = {'BreakoutProgram.getKeyCode', 'BreakoutProgram.getX', 'Bar.moveLeft',
                            'BreakoutProgram.getWidth', 'Bar.moveRight'}
        actual_methods = self.graph_dict['BreakoutProgram']['called_methods']['BreakoutProgram.keyPressed']
        self.assertEqual(actual_methods, expected_methods)


    # -------------------------------- Brick Tests ----------------------------------------------
    def test_Brick_setX(self):
        expected_methods = set()
        actual_methods = self.graph_dict['Brick']['called_methods']['Brick.setX']
        self.assertEqual(actual_methods, expected_methods)


    # -------------------------------- BrickWall Tests ----------------------------------------------
    def test_BrickWall_getBricksRemaining(self):
        expected_methods = set()
        actual_methods = self.graph_dict['BrickWall']['called_methods']['BrickWall.getBricksRemaining']
        self.assertEqual(actual_methods, expected_methods)

    def test_BrickWall_removeBrick(self):
        expected_methods = set()
        actual_methods = self.graph_dict['BrickWall']['called_methods']['BrickWall.removeBrick']
        self.assertEqual(actual_methods, expected_methods)



if __name__ == '__main__':
    unittest.main()
