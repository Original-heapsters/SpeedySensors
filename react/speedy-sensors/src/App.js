import React from "react";
import { render } from "react-dom";
import { withStyles } from "@material-ui/core/styles";
import Chart from "./chart";
import openSocket from 'socket.io-client';

const styles = theme => ({
  "chart-container": {
    height: 400
  }
});

class App extends React.Component {
  state = {
    lineChartData: {
      labels: [],
      datasets: [
        {
          type: "line",
          label: "BTC-USD",
          backgroundColor: "rgba(0, 0, 0, 0)",
          borderColor: this.props.theme.palette.primary.main,
          pointBackgroundColor: this.props.theme.palette.secondary.main,
          pointBorderColor: this.props.theme.palette.secondary.main,
          borderWidth: "2",
          lineTension: 0.45,
          data: []
        }
      ]
    },
    lineChartOptions: {
      responsive: true,
      maintainAspectRatio: false,
      tooltips: {
        enabled: true
      },
      scales: {
        xAxes: [
          {
            ticks: {
              autoSkip: true,
              maxTicksLimit: 10
            }
          }
        ]
      }
    }
  };

  updateCallback(err, data){
    console.log("NICE")
    console.log(data)
    const value = JSON.parse(data);

    const oldBtcDataSet = this.state.lineChartData.datasets[0];
    const newBtcDataSet = { ...oldBtcDataSet };
    newBtcDataSet.data.push(value.price);

    const newChartData = {
      ...this.state.lineChartData,
      datasets: [newBtcDataSet],
      labels: this.state.lineChartData.labels.concat(
        new Date().toLocaleTimeString()
      )
    };
    this.setState({ lineChartData: newChartData });
    this.app_socket.emit("socketboi", {'nice':'cool'});
  }
  getDateTime(){
    var today = new Date();
    var date = today.getFullYear()+'-'+(today.getMonth()+1)+'-'+today.getDate();
    var time = today.getHours() + ":" + today.getMinutes() + ":" + today.getSeconds();
    var dateTime = date+' '+time;
    return dateTime;
  }
  componentDidMount() {
    const subscribe = {
      type: "subscribe",
      channels: [
        {
          name: "ticker",
          product_ids: ["BTC-USD"]
        }
      ]
    };

    this.app_socket = openSocket(process.env.REACT_APP_SOCKET_SVC || "http://localhost:5000");

    this.app_socket.on('update', updates => this.updateCallback(null, updates));
    this.app_socket.emit('connected', {'type':'observer', 'dateTime': this.getDateTime()});
  }

  componentWillUnmount() {
    this.app_socket = null;
  }

  render() {
    const { classes } = this.props;

    return (
      <div className={classes["chart-container"]}>
        <Chart
          data={this.state.lineChartData}
          options={this.state.lineChartOptions}
        />
      </div>
    );
  }
}

export default withStyles(styles, { withTheme: true })(App);
