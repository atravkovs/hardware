use influxdb2::models::DataPoint;
use std::{sync::Arc, time::Duration};

mod client;
use client::HardwareClient;

use clap::Parser;
use clokwerk::{AsyncScheduler, TimeUnits};
use systemstat::{saturating_sub_bytes, Platform, System};

/// Device Client to report it's statistics to InfluxDB. By Artjoms Travkovs at20057
#[derive(Parser, Debug)]
#[command(author, version, about, long_about = None)]
struct Args {
    /// Frequency of reporting in seconds
    #[arg(short, long, default_value_t = 5)]
    interval: u32,

    /// InfluxDB Host
    #[arg(long)]
    host: String,

    /// InfluxDB Authentication token
    #[arg(short, long)]
    token: String,

    /// Hardware Device Code
    #[arg(short, long)]
    device: String,
}

async fn iteration(client: &HardwareClient) -> Result<(), Box<dyn std::error::Error>> {
    let used_memory = get_used_memory().unwrap();

    let points = vec![DataPoint::builder("memory")
        .field("used", used_memory)
        .build()?];

    client.send(points).await.unwrap();

    Ok(())
}

#[tokio::main]
async fn main() -> Result<(), Box<dyn std::error::Error>> {
    let args = Args::parse();
    let mut scheduler = AsyncScheduler::new();
    let client = HardwareClient::new(
        args.host,
        String::from("at20057"),
        args.token,
        args.device,
    );

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

fn get_used_memory() -> Result<i64, Box<dyn std::error::Error>> {
    let sys = System::new();

    match sys.memory() {
        Ok(mem) => {
            let used_memory = saturating_sub_bytes(mem.total, mem.free);

            println!("\nMemory: {} used / {}", used_memory, mem.total);

            Ok(used_memory.as_u64() as i64)
        }
        Err(x) => {
            println!("\nMemory: error: {}", x);

            Err(Box::new(x))
        }
    }
}
