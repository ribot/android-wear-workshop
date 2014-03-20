#!/usr/bin/env ruby

require 'rubygems'
require 'eventmachine'

STDOUT.sync = true

module EventServer  
  def receive_data(data)
    puts data
  end
end

EventMachine::run do
  host = '0.0.0.0'
  port = 9001
  EventMachine::start_server host, port, EventServer
end

