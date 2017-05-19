/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.neuroph.contrib.bpbench;

import org.neuroph.contrib.eval.ClassifierEvaluator;
import org.neuroph.contrib.eval.Evaluation;
import org.neuroph.contrib.eval.classification.ConfusionMatrix;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.learning.LearningRule;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.util.TransferFunctionType;

/**
 * Abstract class that defines needed methods for benchmarking
 *
 * @author Mladen Savic <mladensavic94@gmail.com>
 */
public abstract class AbstractTraining {

    /**
     * Neural network used in specific training
     */
    private final NeuralNetwork neuralNet;

    /**
     * Dataset used in specific training
     */
    private final DataSet dataset;

    /**
     * Statistics that are generated after training is completed
     */
    private TrainingStatistics stats;

    /**
     * Settings used to set up training
     */
    private TrainingSettings settings;

    /**
     * Abstract method for executing training
     */
    public abstract void testNeuralNet();

    /**
     * Abstract method for setting up learning rule
     *
     * @return returns instance of learning rule with parameters
     */
    public abstract LearningRule setParameters();

    /**
     * Method that creates confusion matrix from given dataset and neuraln
     * network
     *
     * @return confusion matrix for this training
     */
    public ConfusionMatrix createMatrix() {
        Evaluation eval = new Evaluation();
        String[] classLabels = new String[dataset.getOutputSize()];
        for (int i = 0; i < dataset.getOutputSize(); i++) {
            classLabels[i] = dataset.getColumnName(dataset.getInputSize() + i);
        }
        eval.addEvaluator(new ClassifierEvaluator.MultiClass(classLabels));
        return eval.evaluateDataSet(neuralNet, dataset).getConfusionMatrix();
    }

    /**
     * Create instance of training with given parameters
     *
     * @param neuralNet
     * @param dataset
     * @param settings
     */
    public AbstractTraining(NeuralNetwork neuralNet, DataSet dataset, TrainingSettings settings) {
        this.neuralNet = neuralNet;
        this.dataset = dataset;
        this.stats = new TrainingStatistics();
        this.settings = settings;
    }

    /**
     * Create instance of training with new neural network
     *
     * @param dataset
     * @param settings
     */
    public AbstractTraining(DataSet dataset, TrainingSettings settings) {
        this.dataset = dataset;
        this.settings = settings;
        this.stats = new TrainingStatistics();
        this.neuralNet = new MultiLayerPerceptron(TransferFunctionType.SIGMOID, dataset.getInputSize(), settings.getHiddenNeurons(), dataset.getOutputSize());
    }

    /**
     * Returns settings for training
     *
     * @return settings for training
     */
    public TrainingSettings getSettings() {
        return settings;
    }

    /**
     * Sets up settings for training
     *
     * @param settings
     */
    public void setSettings(TrainingSettings settings) {
        this.settings = settings;
    }

    /**
     * Returns dataset
     *
     * @return dataset used for training
     */
    public DataSet getDataset() {
        return dataset;
    }

    /**
     * Returns neural network
     *
     * @return neural network learned in this training
     */
    public NeuralNetwork getNeuralNet() {
        return neuralNet;
    }

    /**
     * Returns statistics generated by this training
     *
     * @return statistics
     */
    public TrainingStatistics getStats() {
        return stats;
    }

}