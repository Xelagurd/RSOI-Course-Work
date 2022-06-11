#!/usr/bin/env bash
set -e

export VARIANT=v3
export SCRIPT_PATH=/docker-entrypoint-initdb.d/
export PGPASSWORD=123456
psql -U role2 -d services -f "$SCRIPT_PATH/schemes/schema-$VARIANT.sql"