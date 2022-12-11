use std::{sync::Arc, time::Duration};

mod client;
mod stats;
use client::HardwareClient;

use clap::Parser;
use clokwerk::{AsyncScheduler, TimeUnits};

/// Device Client to report it's statistics to InfluxDB. By Artjoms Travkovs at20057
#[derive(Parser, Debug)]
#[command(author, version, about, long_about = None)]
struct Args {
    /// Frequency of reporting in seconds
    #[arg(short, long, default_value_t = 5)]
    interval: u32,

    /// InfluxDB Host, i.e. http://localhost:8086/
    #[arg(long)]
    host: String,

    /// InfluxDB Authentication token
    #[arg(short, long)]
    token: String,

    /// Hardware Device Code, i.e. 123
    #[arg(short, long)]
    device: String,
}

async fn iteration(client: &HardwareClient) -> Result<(), Box<dyn std::error::Error>> {
    let points = vec![stats::get_memory_point()?, stats::get_cpu_point()?];

    client.send(points).await.unwrap();

    Ok(())
}

#[tokio::main]
async fn main() -> Result<(), Box<dyn std::error::Error>> {
    let args = Args::parse();
    let mut scheduler = AsyncScheduler::new();
    let client = HardwareClient::new(args.host, String::from("at20057"), args.token, args.device);

    let client_arc = Arc::new(client);

    scheduler.every(args.interval.second()).run(move || {
        let cc = client_arc.clone();

        async move {
            iteration(&cc).await.unwrap();
        }
    });

    // Manually run the scheduler forever
    loop {
        scheduler.run_pending().await;
        tokio::time::sleep(Duration::from_millis(10)).await;
    }
}
