#!/bin/bash
echo "Before assembling"
echo 'umask 0002' >> /home/jboss/.bashrc
echo "changed umask of jboss to 0002"
/usr/local/s2i/assemble
rc=$?

if [ $rc -eq 0 ]; then
    echo "After successful assembling"
else
    echo "After failed assembling"
fi

exit $rc