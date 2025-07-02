/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package hostelmanagement;

/**
 *
 * @author inoum
 */
public interface RoomSubject {
    void registerObserver(RoomObserver observer);
    void removeObserver(RoomObserver observer);
    void notifyObservers();
}