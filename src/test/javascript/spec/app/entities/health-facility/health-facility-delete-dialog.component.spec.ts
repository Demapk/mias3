/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { MiasTestModule } from '../../../test.module';
import { HealthFacilityDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/health-facility/health-facility-delete-dialog.component';
import { HealthFacilityService } from '../../../../../../main/webapp/app/entities/health-facility/health-facility.service';

describe('Component Tests', () => {

    describe('HealthFacility Management Delete Component', () => {
        let comp: HealthFacilityDeleteDialogComponent;
        let fixture: ComponentFixture<HealthFacilityDeleteDialogComponent>;
        let service: HealthFacilityService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MiasTestModule],
                declarations: [HealthFacilityDeleteDialogComponent],
                providers: [
                    HealthFacilityService
                ]
            })
            .overrideTemplate(HealthFacilityDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(HealthFacilityDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(HealthFacilityService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
