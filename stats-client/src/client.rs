use futures::stream;
use influxdb2::{models::DataPoint, Client};

#[derive(Clone)]
pub struct HardwareClient {
    client: Client,
    device: String,
}

impl HardwareClient {
    pub fn new(host: String, org: String, token: String, device: String) -> HardwareClient {
        let client = Client::new(host, org, token);

        HardwareClient { client, device }
    }

    pub async fn send(&self, data: Vec<DataPoint>) -> Result<(), Box<dyn std::error::Error>> {
        self.client
            .write(format!("d{}", &self.device).as_str(), stream::iter(data))
            .await?;

        Ok(())
    }
}
