#!/usr/bin/ruby

require 'msgpack'

class Message
	attr_accessor :mid, :owner, :message, :speed, :created_at, :longitude, :latitude, :user_rating
end

messageArray = nil
messageCount = 0
File.open("dump") do |f|
	 # Create a instance of "MessagePack::Unpacker"
	pac = MessagePack::Unpacker.new(f)
	i = 0
	begin
		pac.each do |obj|
			if (i == 0)
				messageCount = obj
				messageArray = Array.new(Integer(messageCount)) { Message.new }
			elsif ((i-1)/8 < messageCount)
				adjusted = (i - 1)%8;
				objnumber = (i - 1)/8
				if (adjusted == 0) 
					messageArray[objnumber].mid = obj
				elsif (adjusted == 1)
					messageArray[objnumber].owner = obj
				elsif (adjusted == 2)
					messageArray[objnumber].message = obj
				elsif (adjusted == 3)
					messageArray[objnumber].speed = obj
				elsif (adjusted == 4)
					messageArray[objnumber].created_at = obj
				elsif (adjusted == 5)
					messageArray[objnumber].longitude = obj
				elsif (adjusted == 6)
					messageArray[objnumber].latitude = obj
				elsif (adjusted == 7)
					messageArray[objnumber].user_rating = obj
				end
			end
			i = i + 1
		end
	rescue EOFError
	end
end

puts "Found #{messageArray.length} message objects. Separating into constituents and dumpng.\n"
messageArray.each do |message|
	fileName = "#{message.mid}.amr"
	puts "Dumping to " + fileName + "...\n";
	File.open(fileName, "w") do |f|
		f.puts message.message
	end
end
