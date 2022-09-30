#!/usr/bin/env bash

open() {
  /usr/bin/open http://localhost:8080/crud/v1/tasks
}

fail() {
  echo "Something went wrong"
}

end() {
  echo "Safari started"
}

if ./runcrud.sh; then
  sleep 3
  open
  end
else
  fail
fi