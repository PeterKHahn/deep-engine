    def graphs(self):
        fig = plt.figure()
        ax = fig.add_subplot(111, projection='3d')
        ax.set_xlabel('X: Position')
        ax.set_ylabel('Y Velocity')
        ax.set_zlabel('Z: Max Q')

        xdata = [] 
        ydata = [] 
        zdata = []
        colors = []
        for i in range(-12, 7):
            for j in range(-7, 8):
                axis1 = i / 10
                axis2 = j / 100
                xdata.append(axis1)
                ydata.append(axis2)
                res = self.session.run(self.q, feed_dict={self.state_input: [[axis1, axis2]], self.initialization: False})
                argmax = np.argmax(res[0])
                if argmax == 0:
                    colors.append('r')
                elif argmax == 1:
                    colors.append('g')
                else:
                    colors.append('b')
                zdata.append(max(res[0]))

        ax.scatter(xdata, ydata, zdata, c=colors)
        plt.show()