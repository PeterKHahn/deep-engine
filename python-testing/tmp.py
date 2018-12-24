import tensorflow as tf


input = tf.placeholder(tf.float32, [3, 5])

output = tf.layers.dense(input, 10)
output = tf.layers.dense(output, 30)

indices = tf.range(0, 10) * 2

x = tf.placeholder(tf.int32, [5, 10])


session = tf.Session()

session.run(tf.global_variables_initializer())

print(output)
print(indices)

print(x + indices)

print(session.run(indices))
