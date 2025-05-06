#!/bin/sh

# Usage: ./wait-for.sh host:port -- command-to-run

HOST_PORT=$1
shift

echo "Waiting for $HOST_PORT..."

while ! nc -z ${HOST_PORT%:*} ${HOST_PORT#*:}; do
  sleep 1
done

echo "$HOST_PORT is available - starting application"
exec "$@"
