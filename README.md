# AWS Vault Configure
Fetches new AWS credentials from a Vault instance using Vault's AWS backend.

## Usage
1. Download the latest release from the Releases tab on GitHub.
2. Ensure that you have an up to date version of Java installed.
3. Run the jar, `java -jar AWSVaultConfigure.jar {Vault AWS path}`. `Vault AWS path` is the secret path to the AWS secret backend that you want to use, like `v1/aws/creds/deploy`.
4. Enter the Vault Root Address and Vault Token as instructed.
5. The new credentials will be saved to your user AWS configuration.  

## Compatability
* Linux

## License & Authors
- Author:: Tyler Wong ([ty-w@live.com](mailto:ty-w@live.com))

```text
Copyright 2017, Tyler Wong

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.```
