import tensorflow as tf
import os
os.environ['TF_CPP_MIN_LOG_LEVEL'] = '2'


x = tf.placeholder(shape=[1, 5], dtype=tf.float32)
layer = tf.layers.dense(x, units=10, activation=tf.nn.relu)

session = tf.Session()
session.run(tf.global_variables_initializer())



x = session.run(layer, feed_dict={x: [[0.5, 0.5, 0.1, 0.0, 0.0]]})
print(x)
