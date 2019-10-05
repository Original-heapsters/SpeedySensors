class AnomalyDetector(object):
    def __init__(self, running_data={}):
        self.running_data = running_data


    def analyze(self, new_data):
        
        pass

    def rolling_avg(self, x, N):
        cumsum = numpy.cumsum(numpy.insert(x, 0, 0))
        return (cumsum[N:] - cumsum[:-N]) / float(N)
