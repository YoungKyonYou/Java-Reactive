package org.youyk.sec12;

import org.youyk.common.Util;
import org.youyk.sec12.assignment.SlackMember;
import org.youyk.sec12.assignment.SlackRoom;

public class Lec08SlackAssignment {

    public static void main(String[] args) {

        SlackRoom room = new SlackRoom("reactor");
        SlackMember sam = new SlackMember("sam");
        SlackMember jake = new SlackMember("jake");
        SlackMember mike = new SlackMember("mike");

        // add 2 members
        room.addMember(sam);
        room.addMember(jake);

        sam.says("Hi all..");
        Util.sleepSeconds(4);

        jake.says("Hey!");
        sam.says("I simply wanted to say hi..");
        Util.sleepSeconds(4);

        room.addMember(mike);
        mike.says("Hey guys..glad to be here...");


    }

}