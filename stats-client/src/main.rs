use futures::prelude::*;
use influxdb2::models::DataPoint;
use influxdb2::Client;
use std::time::Duration;

use clap::Parser;
use clokwerk::{AsyncScheduler, TimeUnits};
use systemstat::{saturating_sub_bytes, Platform, System};

#[derive(Parser, Debug)]
#[command(author, version, about, long_about = None)]
struct Args {
    #[arg(short, long, default_value_t = 5)]
    interval: u32,
}

#[tokio::main]
async fn main() -> Result<(), Box<dyn std::error::Error>> {
    // report().await?;

    let args = Args::parse();
    let mut scheduler = AsyncScheduler::new();

    scheduler.every(args.interval.second()).run(|| async {
        let used_memory = get_used_memory().unwrap();
        send(used_memory).await.unwrap();
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

async fn send(used_memory: i64) -> Result<(), Box<dyn std::error::Error>> {
    let host = "http://localhost:8086/";
    let org = "at20057";
    let token =
        "RAel9ad3CDN3ZqMNF4itRoUBK7oromilf-cMH4blhwlC-HQpCyTK0TWXV-Y9AbQXMPebfxrR9H1hq6q9i6WZfg==";
    let bucket = "test";
    let client = Client::new(host, org, token);

    let points = vec![DataPoint::builder("memory")
        .tag("device", "test123")
        .field("used", used_memory)
        .build()?];

    client.write(bucket, stream::iter(points)).await?;

    Ok(())
}
