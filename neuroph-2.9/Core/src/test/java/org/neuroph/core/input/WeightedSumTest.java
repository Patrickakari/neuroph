package org.neuroph.core.input;

import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.*;
import org.neuroph.core.Connection;
import org.neuroph.core.Neuron;
import org.neuroph.nnet.comp.neuron.InputNeuron;

/**
 *
 * @author Shivanth
 */
public class WeightedSumTest {

    private WeightedSum weightedSum;
    private List<Connection> inputConnections;
    private List<InputNeuron> inputNeurons;

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        weightedSum = new WeightedSum();

        inputNeurons = new ArrayList<>(4);
        for (int i = 0; i < 4; i++) {
            inputNeurons.add(new InputNeuron());
        }

        Neuron toNeuron = new Neuron();

        inputConnections = new ArrayList<Connection>(4);
        for (int i = 0; i < 4; i++) {
            inputConnections.add( new Connection(inputNeurons.get(i), toNeuron, 1));
            toNeuron.addInputConnection(inputConnections.get(i));
        }
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testSumWithRandomInput() {
        double[] inputs = new double[]{1, 3, 5, 7};
        double[] weights = new double[]{.2, 5, 7, 8};

        for (int i = 0; i < inputNeurons.size(); i++) {
            inputConnections.get(i).getWeight().setValue(weights[i]);   
            inputNeurons.get(i).setInput(inputs[i]);
            inputNeurons.get(i).calculate();
        }

        double output = weightedSum.getOutput(inputConnections);
        assertEquals(106.2, output, .000001); // 1e-6 ili 7
    }

    @Test
    public void testOnRandomConnections() {
        double[] inputs = new double[]{1, 3, 5, 7};
        double[] weights = {0.5d, 0.25d, -0.25d, 0.1d};
        
        for (int i = 0; i < inputNeurons.size(); i++) {
            inputConnections.get(i).getWeight().setValue(weights[i]);   
            inputNeurons.get(i).setInput(inputs[i]);
            inputNeurons.get(i).calculate();            
        }

        double output = weightedSum.getOutput(inputConnections);
        assertEquals(0.7, output, .000001);
    }
}
