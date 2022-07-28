const fs = require('fs');
const { exec } = require('child_process');

/*
 Creates a typescript file with the current 'git describe' output to be
 included in builds

 Command line options:
    generate (default): create the src/environments/gitVersion.ts file
    clean: delete the generated gitVersion.ts
*/

const versionFile = 'src/environments/buildInfo.ts';

switch (process.argv[2]){
    case "clean":
        try {
            fs.unlinkSync(versionFile);
        } catch (err) {
            // Non-existent file (ENOENT) errors are fine since we're
            // deleting a file. Anything else should be thrown
            if(err.code !== 'ENOENT') { throw err; }
        }
        console.log(`Deleted ${versionFile}`)
        break;

    case "generate":
    default:
        exec('git describe --always --dirty', (err, stdout, stderr) => {
            if(err || stderr) {
                throw new Error(`Could not get git describe information (err code: ${err.code}, message: ${err.message})`)
            }

            fs.writeFileSync(versionFile, (
              `export const buildInfo = {
                  version: '${stdout.replace('\n','')}',
                  time: '${new Date().toISOString()}'
              }
              `));
        });
        console.log(`Created ${versionFile}`)
        break;
}

