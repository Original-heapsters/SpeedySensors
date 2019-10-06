import numpy
class AnomalyDetector(object):
    def __init__(self, running_data={}):
        '''
        {
            "gwijoaas09-wg5sdfs4rege-w4h54h-w4hw5wegeg":{
            "data":
                [
                    [x,y,z],
                    [x,y,z],
                    [x,y,z]
                ]
            "rolling_x":0.0,
            "rolling_y":0.0,
            "rolling_z":0.0
            }

        }
        '''
        self.running_data = running_data
        self.lookback = 5
        self.max_consideration = 100


    def analyze(self, new_data):
        '''
        {
            "id":"gwijoaas09-wg5sdfs4rege-w4h54h-w4hw5wegeg",
            "accelerometer": [0.0,0.0,0.0]
        }
        '''
        if new_data["id"] not in self.running_data:
            self.running_data[new_data["id"]] = {}
        dataset = self.running_data[new_data["id"]]
        if "data" not in dataset:
            dataset["data"] = []
        dataset["data"].append(new_data["accelerometer"])

        if len(dataset["data"]) > self.max_consideration:
            dataset["data"].pop(0)

        x_vals = [x[0] for x in dataset["data"][:-self.lookback]]
        y_vals = [x[1] for x in dataset["data"][:-self.lookback]]
        z_vals = [x[2] for x in dataset["data"][:-self.lookback]]
        dataset["rolling_x"] = self.rolling_avg(x_vals, len(x_vals))
        dataset["rolling_y"] = self.rolling_avg(y_vals, len(y_vals))
        dataset["rolling_z"] = self.rolling_avg(z_vals, len(z_vals))
        data_len = len(dataset["data"])
        start_idx = max(0,data_len - self.lookback)
        lookback_x = [x[0] for x in dataset["data"][start_idx:]]
        lookback_y = [x[1] for x in dataset["data"][start_idx:]]
        lookback_z = [x[2] for x in dataset["data"][start_idx:]]

        anomaly_x = abs(self.rolling_avg(lookback_x, len(lookback_x)) - dataset["rolling_x"])
        anomaly_y = abs(self.rolling_avg(lookback_y, len(lookback_y)) - dataset["rolling_y"])
        anomaly_z = abs(self.rolling_avg(lookback_z, len(lookback_z)) - dataset["rolling_z"])
        print(anomaly_x)
        print(anomaly_y)
        print(anomaly_z)

        if anomaly_x > 1 and len(dataset["data"]) > self.lookback:
            print("X ANOMALY")
            return {\
            "id":new_data["id"],\
            "anomaly": "x",\
            "magnitude":anomaly_x}
        if anomaly_y > 1 and len(dataset["data"]) > self.lookback:
            print("Y ANOMALY")
            return {\
            "id":new_data["id"],\
            "anomaly": "y",\
            "magnitude":anomaly_y}
        if anomaly_z > 1 and len(dataset["data"]) > self.lookback:
            print("Z ANOMALY")
            return {\
            "id":new_data["id"],\
            "anomaly": "z",\
            "magnitude":anomaly_z}

        print(self.running_data)
        return None

    def rolling_avg(self, x, N):
        cumsum = numpy.cumsum(numpy.insert(x, 0, 0))
        avg = (cumsum[N:] - cumsum[:-N]) / float(N)
        if len(avg) > 0:
            return avg[0]
        else:
            return 0
