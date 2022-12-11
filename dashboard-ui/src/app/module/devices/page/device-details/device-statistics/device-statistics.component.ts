import { Component, Input, OnInit } from '@angular/core';
import { ChartConfiguration } from 'chart.js';
import { map, Observable, shareReplay } from 'rxjs';
import { DataPoint } from '../../../models/data-point.model';
import { HardwareRepositoryService } from '../../../services/hardware.repository.service';
import prettyBytes from 'pretty-bytes';

@Component({
  selector: 'app-device-statistics',
  templateUrl: './device-statistics.component.html',
  styleUrls: ['./device-statistics.component.scss'],
})
export class DeviceStatisticsComponent implements OnInit {
  @Input()
  deviceCode: number = 0;

  deviceData$: Observable<DataPoint[]> | null = null;
  deviceChartData$: Observable<ChartConfiguration<'line'>['data']> | null =
    null;

  chartOptions: ChartConfiguration['options'] = {
    scales: {
      y: {
        ticks: {
          callback: function (value, index, values) {
            return prettyBytes(+value);
          },
        },
      },
    },
    plugins: {
      tooltip: {
        callbacks: {
          label: function(item) {
            return prettyBytes(item.raw as number);
          }
        }
      }
    }
  };

  constructor(private hardwareRepository: HardwareRepositoryService) {}

  ngOnInit(): void {
    const fromDate = new Date();
    // fromDate.setHours(fromDate.getHours() - 1);
    fromDate.setMinutes(fromDate.getMinutes() - 15);

    this.deviceData$ = this.hardwareRepository
      .getStatistics(this.deviceCode, fromDate, new Date())
      .pipe(shareReplay(1));

    this.deviceChartData$ = this.deviceData$.pipe(
      map((deviceData) => {
        const labels = deviceData.map((data) => data.time.toLocaleTimeString());
        const data = deviceData.map((point) => point.value);

        return {
          labels,
          datasets: [
            {
              data,
              label: 'Used Memory',
              fill: true,
              tension: 0.1,
              pointRadius: 2,
              pointBorderWidth: 0,
              showLine: false,
              borderColor: 'black',
              backgroundColor: 'rgba(255,0,0,0.3)',
            },
          ],
        };
      })
    );
  }
}
