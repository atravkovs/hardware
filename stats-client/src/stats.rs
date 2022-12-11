use std::thread;

use influxdb2::models::DataPoint;
use systemstat::{saturating_sub_bytes, Duration, Platform, System};

struct MemoryLoad {
    used: i64,
    free: i64,
}

struct CpuLoad {
    user: f64,
    system: f64,
    idle: f64,
}

fn get_used_memory() -> Result<MemoryLoad, Box<dyn std::error::Error>> {
    let sys = System::new();

    match sys.memory() {
        Ok(mem) => {
            let used_memory = saturating_sub_bytes(mem.total, mem.free);

            Ok(MemoryLoad {
                used: used_memory.as_u64() as i64,
                free: mem.free.as_u64() as i64,
            })
        }
        Err(x) => {
            println!("\nMemory: error: {}", x);

            Err(Box::new(x))
        }
    }
}

fn get_used_cpu() -> Result<CpuLoad, Box<dyn std::error::Error>> {
    let sys = System::new();

    match sys.cpu_load_aggregate() {
        Ok(cpu) => {
            thread::sleep(Duration::from_secs(1));

            let cpu = cpu.done().unwrap();

            Ok(CpuLoad {
                user: (cpu.user * 100.0) as f64,
                idle: (cpu.idle * 100.0) as f64,
                system: (cpu.system * 100.0) as f64,
            })
        }
        Err(x) => {
            println!("\nCPU Load: error: {}", x);

            Err(Box::new(x))
        }
    }
}

pub fn get_memory_point() -> Result<DataPoint, Box<dyn std::error::Error>> {
    let used_memory = get_used_memory()?;

    let data_point = DataPoint::builder("memory")
        .field("used", used_memory.used)
        .field("free", used_memory.free)
        .build()?;

    Ok(data_point)
}

pub fn get_cpu_point() -> Result<DataPoint, Box<dyn std::error::Error>> {
    let cpu_stats = get_used_cpu()?;

    let data_point = DataPoint::builder("cpu")
        .field("user", cpu_stats.user)
        .field("system", cpu_stats.system)
        .field("idle", cpu_stats.idle)
        .build()?;

    Ok(data_point)
}
